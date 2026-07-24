package com.hwhub.backend.application.service;

import java.util.Locale;
import java.util.Set;
import org.springframework.security.access.AccessDeniedException;

/**
 * アップロード画像（添付・アイコン）の fileKey / 拡張子 / mimeType をサーバー側で検証するための共有ポリシー。
 *
 * <p>クライアント由来の値をそのまま信頼せず、本クラスで画像 allowlist への限定とプレフィックス強制を行う。 ShoppingItemAttachmentService と
 * UserIconService の両経路から利用される。
 */
public final class UploadImagePolicy {

  private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
  private static final Set<String> ALLOWED_MIME_TYPES =
      Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

  private UploadImagePolicy() {}

  /**
   * ファイル名から安全な拡張子（先頭ドット付き・小文字）を抽出する。
   *
   * <p>パス区切り（{@code /} {@code \}）より前の部分は無視し、最後のセグメントのみから拡張子を判定するため、 {@code "a.jpg/../../evil"}
   * のようなファイル名を渡してもパストラバーサルがキーに混入しない。
   *
   * @param fileName アップロードされたファイル名
   * @return 拡張子（例: ".jpg"）。拡張子が無い場合は空文字
   * @throws IllegalArgumentException 拡張子が画像 allowlist に含まれない場合
   */
  public static String sanitizeExtension(String fileName) {
    String raw = extractRawExtension(fileName);
    if (raw.isEmpty()) {
      return "";
    }

    String normalized = raw.toLowerCase(Locale.ROOT);
    if (!ALLOWED_EXTENSIONS.contains(normalized)) {
      throw new IllegalArgumentException("許可されていないファイル形式です");
    }
    return "." + normalized;
  }

  /**
   * mimeType が画像 allowlist に含まれることを検証する。
   *
   * @param mimeType アップロード対象の MIME タイプ
   * @throws IllegalArgumentException 画像 allowlist に含まれない場合
   */
  public static void assertAllowedMimeType(String mimeType) {
    if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType.toLowerCase(Locale.ROOT))) {
      throw new IllegalArgumentException("許可されていない画像形式です");
    }
  }

  /**
   * fileKey が期待するプレフィックス配下の単一セグメントであることを検証する。
   *
   * <p>クライアントは fileKey を自由に指定できるため、サーバー側でプレフィックスを強制することで 他世帯・他ユーザーの S3 オブジェクトへの越境アクセスを防ぐ。
   *
   * @param fileKey 検証対象の fileKey
   * @param expectedPrefix 期待するプレフィックス（末尾は "/"）
   * @throws AccessDeniedException プレフィックス不一致、またはプレフィックス以降に "/" "・.." を含む場合
   */
  public static void assertKeyWithinPrefix(String fileKey, String expectedPrefix) {
    if (fileKey == null || !fileKey.startsWith(expectedPrefix)) {
      throw new AccessDeniedException("不正なファイルキーです");
    }

    String remainder = fileKey.substring(expectedPrefix.length());
    if (remainder.contains("/") || remainder.contains("..")) {
      throw new AccessDeniedException("不正なファイルキーです");
    }
  }

  private static String extractRawExtension(String fileName) {
    if (fileName == null) return "";

    String base = fileName;
    int lastSlash = Math.max(base.lastIndexOf('/'), base.lastIndexOf('\\'));
    if (lastSlash != -1) {
      base = base.substring(lastSlash + 1);
    }

    int dot = base.lastIndexOf('.');
    if (dot == -1) return "";
    return base.substring(dot + 1);
  }
}
