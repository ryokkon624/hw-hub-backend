package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.ShoppingItemAttachment;
import com.hwhub.backend.domain.repository.ShoppingItemAttachmentRepository;
import com.hwhub.backend.domain.repository.ShoppingItemRepository;
import com.hwhub.backend.domain.storage.ObjectStorageClient;
import com.hwhub.backend.infrastructure.s3.ObjectStorageConfig.ShoppingItemStorageSettings;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingItemAttachmentService {

  private final UserService userService;
  private final ShoppingItemRepository shoppingItemRepository;
  private final ShoppingItemAttachmentRepository attachmentRepository;
  private final ObjectStorageClient objectStorageClient;
  private final ShoppingItemStorageSettings storageSettings;

  // presigned PUT URL 発行
  public CreateUploadUrlResult createUploadUrl(
      Long shoppingItemId, String fileName, String mimeType, Long userId) {
    var item =
        shoppingItemRepository
            .findById(shoppingItemId)
            .orElseThrow(() -> new ResourceNotFoundException("Attachment file not found"));

    // household 認可チェック
    List<HouseholdModel> list = userService.getHouseholds(userId);
    if (list.stream().noneMatch(e -> e.getHouseholdId().equals(item.getHouseholdId()))) {
      throw new IllegalStateException("household mismatch");
    }

    String ext = extractExtension(fileName);
    String key = buildFileKey(item.getHouseholdId(), shoppingItemId, ext);

    URL url =
        objectStorageClient.createPresignedPutUrl(
            storageSettings.bucket(), key, mimeType, storageSettings.urlTtl());

    return new CreateUploadUrlResult(url.toString(), key);
  }

  // アップロード完了後にメタ情報を登録
  public ShoppingItemAttachment createAttachment(
      Long shoppingItemId, String fileKey, String fileName, String mimeType, Long userId) {
    var item = shoppingItemRepository.findById(shoppingItemId).orElseThrow();

    // household 認可チェック
    List<HouseholdModel> list = userService.getHouseholds(userId);
    if (list.stream().noneMatch(e -> e.getHouseholdId().equals(item.getHouseholdId()))) {
      throw new IllegalStateException("household mismatch");
    }

    int nextSortOrder =
        attachmentRepository.findByShoppingItemId(shoppingItemId).stream()
                .map(ShoppingItemAttachment::getSortOrder)
                .max(Integer::compareTo)
                .orElse(0)
            + 1;

    ShoppingItemAttachment model =
        ShoppingItemAttachment.create(shoppingItemId, fileKey, fileName, mimeType, nextSortOrder);
    return attachmentRepository.save(model, userId, ProgramType.ONL_SHPATCH.getCode());
  }

  // 一覧 + presigned GET URL 付き
  @Transactional(readOnly = true)
  public List<ShoppingItemAttachment> listAttachments(Long shoppingItemId, Long userId) {
    var item = shoppingItemRepository.findById(shoppingItemId).orElseThrow();

    // household 認可チェック
    List<HouseholdModel> list = userService.getHouseholds(userId);
    if (list.stream().noneMatch(e -> e.getHouseholdId().equals(item.getHouseholdId()))) {
      throw new IllegalStateException("household mismatch");
    }

    var attachmentList = attachmentRepository.findByShoppingItemId(shoppingItemId);

    return attachmentList.stream()
        .map(
            a -> {
              URL url =
                  objectStorageClient.createPresignedGetUrl(
                      storageSettings.bucket(), a.getFileKey(), storageSettings.urlTtl());
              a.setImageUrl(url.toString());
              return a;
            })
        .toList();
  }

  public void deleteAttachment(Long shoppingItemId, Long attachmentId, Long userId) {
    var item = shoppingItemRepository.findById(shoppingItemId).orElseThrow();

    // household 認可チェック
    List<HouseholdModel> list = userService.getHouseholds(userId);
    if (list.stream().noneMatch(e -> e.getHouseholdId().equals(item.getHouseholdId()))) {
      throw new IllegalStateException("household mismatch");
    }

    var attachment = attachmentRepository.findById(attachmentId).orElseThrow();

    if (!attachment.getShoppingItemId().equals(shoppingItemId)) {
      throw new IllegalStateException("shoppingItem mismatch");
    }

    // S3のオブジェクトは消さない。別のアイテムが同じ file_key を使っている可能性があるため
    // objectStorageClient.deleteObject(storageSettings.bucket(), attachment.getFileKey());
    attachmentRepository.deleteById(attachmentId);
  }

  public record CreateUploadUrlResult(String uploadUrl, String fileKey) {}

  private String buildFileKey(Long householdId, Long shoppingItemId, String ext) {
    String uuid = UUID.randomUUID().toString();
    // shopping-item/{householdId}/{shoppingItemId}/{uuid}.ext
    return "%s/%d/%d/%s%s"
        .formatted(storageSettings.shoppingItemBasePath(), householdId, shoppingItemId, uuid, ext);
  }

  private String extractExtension(String fileName) {
    if (fileName == null) return "";
    int dot = fileName.lastIndexOf('.');
    if (dot == -1) return "";
    return fileName.substring(dot); // ".jpg" みたいな形
  }
}
