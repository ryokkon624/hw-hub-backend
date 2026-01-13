package com.hwhub.backend.infrastructure.s3;

import com.hwhub.backend.domain.storage.ObjectStorageClient;
import java.net.URI;
import java.time.Duration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@EnableConfigurationProperties({ObjectStorageProperties.class, AwsS3Properties.class})
public class ObjectStorageConfig {

  private final ObjectStorageProperties objectStorageProperties;
  private final AwsS3Properties awsS3Properties;

  public ObjectStorageConfig(
      ObjectStorageProperties objectStorageProperties, AwsS3Properties awsS3Properties) {
    this.objectStorageProperties = objectStorageProperties;
    this.awsS3Properties = awsS3Properties;
  }

  @Bean
  public S3Client s3Client() {
    String region =
        awsS3Properties.getRegion() != null ? awsS3Properties.getRegion() : "ap-northeast-1";

    S3Configuration serviceConfig =
        S3Configuration.builder()
            .pathStyleAccessEnabled(awsS3Properties.isPathStyleAccessEnabled())
            .build();

    S3ClientBuilder builder =
        S3Client.builder().region(Region.of(region)).serviceConfiguration(serviceConfig);

    String endpoint = awsS3Properties.getEndpoint();
    if (endpoint != null && !endpoint.isBlank()) {
      // localstack
      String accessKey = awsS3Properties.getAccessKey();
      String secretKey = awsS3Properties.getSecretKey();
      AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

      builder =
          builder
              .endpointOverride(URI.create(endpoint))
              .credentialsProvider(StaticCredentialsProvider.create(credentials));
    } else {
      // ECS/本番想定：Task Role などのデフォルトチェーン
      builder = builder.credentialsProvider(DefaultCredentialsProvider.builder().build());
    }

    return builder.build();
  }

  @Bean
  public S3Presigner s3Presigner() {
    String region =
        awsS3Properties.getRegion() != null ? awsS3Properties.getRegion() : "ap-northeast-1";

    S3Configuration serviceConfig =
        S3Configuration.builder()
            .pathStyleAccessEnabled(awsS3Properties.isPathStyleAccessEnabled())
            .build();

    S3Presigner.Builder builder =
        S3Presigner.builder().region(Region.of(region)).serviceConfiguration(serviceConfig);

    String endpoint = awsS3Properties.getEndpoint();
    if (endpoint != null && !endpoint.isBlank()) {
      String accessKey = awsS3Properties.getAccessKey();
      String secretKey = awsS3Properties.getSecretKey();
      AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

      builder =
          builder
              .endpointOverride(URI.create(endpoint))
              .credentialsProvider(StaticCredentialsProvider.create(credentials));
    } else {
      builder = builder.credentialsProvider(DefaultCredentialsProvider.builder().build());
    }

    return builder.build();
  }

  @Bean
  public ObjectStorageClient objectStorageClient(S3Client s3Client, S3Presigner presigner) {
    // ★ S3ObjectStorageClient に @Component は付けないで、ここで new する
    return new S3ObjectStorageClient(s3Client, presigner);
  }

  @Bean
  public ShoppingItemStorageSettings shoppingItemStorageSettings() {
    return new ShoppingItemStorageSettings(
        objectStorageProperties.getBucket(),
        objectStorageProperties.getBasePath().getShoppingItem(),
        Duration.ofSeconds(objectStorageProperties.getUrlTtlSeconds()));
  }

  public record ShoppingItemStorageSettings(
      String bucket, String shoppingItemBasePath, Duration urlTtl) {}

  @Bean
  public UserIconStorageSettings userIconStorageSettings() {
    return new UserIconStorageSettings(
        objectStorageProperties.getBucket(),
        objectStorageProperties.getBasePath().getUserIcon(),
        Duration.ofSeconds(objectStorageProperties.getUrlTtlSeconds()));
  }

  public record UserIconStorageSettings(String bucket, String userIconBasePath, Duration urlTtl) {}
}
