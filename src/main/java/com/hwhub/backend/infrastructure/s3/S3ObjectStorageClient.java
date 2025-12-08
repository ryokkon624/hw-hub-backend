package com.hwhub.backend.infrastructure.s3;

import com.hwhub.backend.domain.storage.ObjectStorageClient;
import java.net.URL;
import java.time.Duration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

public class S3ObjectStorageClient implements ObjectStorageClient {

  private final S3Client s3Client;
  private final S3Presigner presigner;

  public S3ObjectStorageClient(S3Client s3Client, S3Presigner presigner) {
    this.s3Client = s3Client;
    this.presigner = presigner;
  }

  @Override
  public URL createPresignedPutUrl(String bucket, String key, String contentType, Duration ttl) {

    PutObjectRequest putObjectRequest =
        PutObjectRequest.builder().bucket(bucket).key(key).contentType(contentType).build();

    PutObjectPresignRequest presignRequest =
        PutObjectPresignRequest.builder()
            .signatureDuration(ttl)
            .putObjectRequest(putObjectRequest)
            .build();

    return presigner.presignPutObject(presignRequest).url();
  }

  @Override
  public URL createPresignedGetUrl(String bucket, String key, Duration ttl) {

    GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();

    GetObjectPresignRequest presignRequest =
        GetObjectPresignRequest.builder()
            .signatureDuration(ttl)
            .getObjectRequest(getObjectRequest)
            .build();

    return presigner.presignGetObject(presignRequest).url();
  }

  @Override
  public void deleteObject(String bucket, String key) {
    DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucket).key(key).build();

    s3Client.deleteObject(request);
  }
}
