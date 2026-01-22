package com.hwhub.backend.infrastructure.s3

import spock.lang.Specification
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

import java.time.Duration

class S3ObjectStorageClientSpec extends Specification {

    S3Client s3Client = Mock()
    S3Presigner presigner = Mock()
    S3ObjectStorageClient client = new S3ObjectStorageClient(s3Client, presigner)

    def "createPresignedPutUrlは署名付きPUT URLを生成する"() {
        given:
        String bucket = "test-bucket"
        String key = "test-key"
        String contentType = "image/png"
        Duration ttl = Duration.ofMinutes(10)
        URL expectedUrl = new URL("https://s3.example.com/bucket/key")

        def presignedRequest = Mock(PresignedPutObjectRequest) {
            url() >> expectedUrl
        }

        when:
        URL result = client.createPresignedPutUrl(bucket, key, contentType, ttl)

        then:
        1 * presigner.presignPutObject(_ as PutObjectPresignRequest) >> { PutObjectPresignRequest req ->
            assert req.signatureDuration() == ttl
            assert req.putObjectRequest().bucket() == bucket
            assert req.putObjectRequest().key() == key
            assert req.putObjectRequest().contentType() == contentType
            return presignedRequest
        }
        result == expectedUrl
    }

    def "createPresignedGetUrlは署名付きGET URLを生成する"() {
        given:
        String bucket = "test-bucket"
        String key = "test-key"
        Duration ttl = Duration.ofMinutes(10)
        URL expectedUrl = new URL("https://s3.example.com/bucket/key")

        def presignedRequest = Mock(PresignedGetObjectRequest) {
            url() >> expectedUrl
        }

        when:
        URL result = client.createPresignedGetUrl(bucket, key, ttl)

        then:
        1 * presigner.presignGetObject(_ as GetObjectPresignRequest) >> { GetObjectPresignRequest req ->
            assert req.signatureDuration() == ttl
            assert req.getObjectRequest().bucket() == bucket
            assert req.getObjectRequest().key() == key
            return presignedRequest
        }
        result == expectedUrl
    }

    def "deleteObjectはオブジェクト削除リクエストを送信する"() {
        given:
        String bucket = "test-bucket"
        String key = "test-key"

        when:
        client.deleteObject(bucket, key)

        then:
        1 * s3Client.deleteObject(_ as DeleteObjectRequest) >> { DeleteObjectRequest req ->
            assert req.bucket() == bucket
            assert req.key() == key
            return null
        }
    }
}
