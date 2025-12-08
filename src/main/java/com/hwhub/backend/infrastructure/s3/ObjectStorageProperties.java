package com.hwhub.backend.infrastructure.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hwhub.object-storage")
public class ObjectStorageProperties {

  private String bucket;
  private BasePath basePath = new BasePath();
  private long urlTtlSeconds;

  public static class BasePath {
    private String shoppingItem;
    private String userIcon;

    public String getShoppingItem() {
      return shoppingItem;
    }

    public void setShoppingItem(String shoppingItem) {
      this.shoppingItem = shoppingItem;
    }

    public String getUserIcon() {
      return userIcon;
    }

    public void setUserIcon(String userIcon) {
      this.userIcon = userIcon;
    }
  }

  // getter / setter
  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public BasePath getBasePath() {
    return basePath;
  }

  public void setBasePath(BasePath basePath) {
    this.basePath = basePath;
  }

  public long getUrlTtlSeconds() {
    return urlTtlSeconds;
  }

  public void setUrlTtlSeconds(long urlTtlSeconds) {
    this.urlTtlSeconds = urlTtlSeconds;
  }
}
