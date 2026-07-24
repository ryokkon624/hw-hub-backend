package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.domain.storage.ObjectStorageClient;
import com.hwhub.backend.infrastructure.s3.ObjectStorageConfig;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserIconService {

  private final UserRepository userRepository;
  private final ObjectStorageClient objectStorageClient;
  private final ObjectStorageConfig.UserIconStorageSettings storageSettings;

  /**
   * ユーザのプロフィール画像upload用のpresigned URLとキーを返す。 画像uploadまでは3段階ある。
   *
   * <ol>
   *   <li>presigned URL取得 ★当メソッドがこれ
   *   <li>presigned URLを利用しファイルをupload(フロントからAPIをコールすること)
   *   <li>uploadした画像のキーをユーザマスタに登録
   * </ol>
   *
   * @param userId ユーザID
   * @param fileName uploadするファイルの名前
   * @param mimeType MIME type
   * @return 画像upload用のpresigned URL
   */
  public CreateIconUploadUrlResult createUploadUrl(Long userId, String fileName, String mimeType) {

    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalStateException("user not found"));

    UploadImagePolicy.assertAllowedMimeType(mimeType);
    String ext = UploadImagePolicy.sanitizeExtension(fileName);
    String key = buildFileKey(user.getUserId(), ext);

    URL url =
        objectStorageClient.createPresignedPutUrl(
            storageSettings.bucket(), key, mimeType, storageSettings.urlTtl());

    return new CreateIconUploadUrlResult(url.toString(), key);
  }

  /**
   * iconのキーを登録する。
   *
   * <ol>
   *   <li>presigned URL取得
   *   <li>presigned URLを利用しファイルをupload(フロントからAPIをコールすること)
   *   <li>uploadした画像のキーをユーザマスタに登録 ★当メソッドがこれ
   * </ol>
   *
   * @param userId ユーザID
   * @param fileKey uploadした画像のキー(#createUploadUrl()で生成されたキー)
   */
  public void updateUserIcon(Long userId, String fileKey) {
    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalStateException("user not found"));

    String expectedPrefix = "%s/%d/".formatted(storageSettings.userIconBasePath(), userId);
    UploadImagePolicy.assertKeyWithinPrefix(fileKey, expectedPrefix);

    String oldKey = user.getProfileImageKey();
    // 古いアイコンがあれば S3 から削除（失敗しても本処理は続行
    if (oldKey != null && !oldKey.equals(fileKey)) {
      try {
        objectStorageClient.deleteObject(storageSettings.bucket(), oldKey);
      } catch (Exception e) {
        // 処理継続
      }
    }

    user.changeProfileImageKey(fileKey);
    userRepository.updateProfileImgKey(user, ProgramType.ONL_USRICON.getCode());
  }

  /**
   * プロフィール画像をフロントからアクセスするためのURLを返す。
   *
   * @param userId ユーザID
   * @return プロフィール画像のURL
   */
  @Transactional(readOnly = true)
  public String getIconUrl(Long userId) {
    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalStateException("user not found"));

    return getIconUrl(user.getProfileImageKey());
  }

  /**
   * プロフィール画像をフロントからアクセスするためのURLを返す。
   *
   * @param fileKey プロフィール画像のキー
   * @return プロフィール画像のURL
   */
  public String getIconUrl(String fileKey) {
    if (fileKey == null || fileKey.isBlank()) {
      return null;
    }

    URL url =
        objectStorageClient.createPresignedGetUrl(
            storageSettings.bucket(), fileKey, storageSettings.urlTtl());
    return url.toString();
  }

  // Presentation層に渡すDTO
  public record CreateIconUploadUrlResult(String uploadUrl, String fileKey) {}

  /**
   * プロフィール画像のキーを生成する。<br>
   * user-icon/{$userId}/icon{$ext}形式。ex) user-icon/10/icon.jpg
   *
   * @param userId ユーザID
   * @param ext 拡張子
   * @return プロフィール画像のキー
   */
  private String buildFileKey(Long userId, String ext) {
    // user-icon/{userId}/icon.jpg のイメージ
    return "%s/%d/icon%s".formatted(storageSettings.userIconBasePath(), userId, ext);
  }
}
