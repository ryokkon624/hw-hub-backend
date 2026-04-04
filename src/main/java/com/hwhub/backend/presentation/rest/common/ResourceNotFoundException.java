package com.hwhub.backend.presentation.rest.common;

/** 指定されたリソースが見つからない場合に投げられる汎用的な例外。 家事、世帯、招待、問い合わせなど、ID指定による取得で対象が存在しない場合に使用される。 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
