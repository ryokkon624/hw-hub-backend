package com.hwhub.backend.domain.storage;

import java.net.URL;
import java.time.Duration;

public interface ObjectStorageClient {

  URL createPresignedPutUrl(String bucket, String key, String contentType, Duration ttl);

  URL createPresignedGetUrl(String bucket, String key, Duration ttl);

  void deleteObject(String bucket, String key);
}
