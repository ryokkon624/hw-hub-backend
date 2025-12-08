package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.domain.model.ShoppingItemAttachment
import com.hwhub.backend.domain.model.ShoppingItemModel
import com.hwhub.backend.domain.repository.ShoppingItemAttachmentRepository
import com.hwhub.backend.domain.repository.ShoppingItemRepository
import com.hwhub.backend.domain.storage.ObjectStorageClient
import com.hwhub.backend.infrastructure.s3.ObjectStorageConfig.ShoppingItemStorageSettings
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import spock.lang.Specification

import java.net.URL
import java.util.Optional
import java.time.Duration

class ShoppingItemAttachmentServiceSpec extends Specification {

    UserService userService = Mock()
    ShoppingItemRepository shoppingItemRepository = Mock()
    ShoppingItemAttachmentRepository attachmentRepository = Mock()
    ObjectStorageClient objectStorageClient = Mock()
    ShoppingItemStorageSettings storageSettings =
            new ShoppingItemStorageSettings(
                    "bucket-name",
                    "shopping-item",
                    Duration.ofSeconds(300)
            )

    ShoppingItemAttachmentService service = new ShoppingItemAttachmentService(
            userService,
            shoppingItemRepository,
            attachmentRepository,
            objectStorageClient,
            storageSettings
    )

    // ==================================
    // createUploadUrl
    // ==================================

    def "createUploadUrlはアイテムが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        Long shoppingItemId = 100L

        when:
        service.createUploadUrl(shoppingItemId, "image.png", "image/png", 10L)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "createUploadUrlはhouseholdが一致しない場合IllegalStateExceptionを投げる"() {
        given:
        Long shoppingItemId = 100L
        Long userId = 10L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> 1L
        }
        def otherHousehold = Mock(HouseholdModel) {
            getHouseholdId() >> 2L
        }

        when:
        service.createUploadUrl(shoppingItemId, "image.png", "image/png", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [otherHousehold]
        0 * objectStorageClient.createPresignedPutUrl(_, _, _, _)

        thrown(IllegalStateException)
    }

    def "createUploadUrlは認可OKならpresigned PUT URLとfileKeyを返す"() {
        given:
        Long shoppingItemId = 100L
        Long userId = 10L
        Long householdId = 1L
        String fileName = "photo.jpg"
        String mimeType = "image/jpeg"

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        when:
        def result = service.createUploadUrl(shoppingItemId, fileName, mimeType, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]

        1 * objectStorageClient.createPresignedPutUrl(
                "bucket-name",
                { String key ->
                    assert key.startsWith("shopping-item/${householdId}/${shoppingItemId}/")
                    assert key.endsWith(".jpg")
                    true
                },
                mimeType,
                Duration.ofSeconds(300)
        ) >> new URL("https://example.com/upload-url")

        and:
        result.uploadUrl == "https://example.com/upload-url"
        result.fileKey.startsWith("shopping-item/${householdId}/${shoppingItemId}/")
        result.fileKey.endsWith(".jpg")
    }

    def "createUploadUrlは拡張子なしファイル名のときfileKeyに拡張子が含まれない"() {
        given:
        Long shoppingItemId = 101L
        Long userId = 10L
        Long householdId = 1L
        String fileName = "noextension"   // 拡張子なし
        String mimeType = "image/jpeg"

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        when:
        def result = service.createUploadUrl(shoppingItemId, fileName, mimeType, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]

        1 * objectStorageClient.createPresignedPutUrl(
                "bucket-name",
                { String key ->
                    assert key.startsWith("shopping-item/${householdId}/${shoppingItemId}/")
                    // 最後の「/」以降にドットが無い → 拡張子なし
                    def filenamePart = key.substring(key.lastIndexOf('/') + 1)
                    assert !filenamePart.contains(".")
                    true
                },
                mimeType,
                Duration.ofSeconds(300)
        ) >> new URL("https://example.com/upload-url-noext")

        and:
        def fileKeyNamePart = result.fileKey.substring(result.fileKey.lastIndexOf('/') + 1)
        !fileKeyNamePart.contains(".")
        result.uploadUrl == "https://example.com/upload-url-noext"
    }

    def "createUploadUrlはfileNameがnullのときも拡張子なしでキーを生成する"() {
        given:
        Long shoppingItemId = 102L
        Long userId = 10L
        Long householdId = 1L
        String mimeType = "image/jpeg"

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        when:
        def result = service.createUploadUrl(shoppingItemId, null, mimeType, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]

        1 * objectStorageClient.createPresignedPutUrl(
                "bucket-name",
                { String key ->
                    assert key.startsWith("shopping-item/${householdId}/${shoppingItemId}/")
                    def filenamePart = key.substring(key.lastIndexOf('/') + 1)
                    assert !filenamePart.contains(".")
                    true
                },
                mimeType,
                Duration.ofSeconds(300)
        ) >> new URL("https://example.com/upload-url-nullname")

        and:
        def fileKeyNamePart = result.fileKey.substring(result.fileKey.lastIndexOf('/') + 1)
        !fileKeyNamePart.contains(".")
        result.uploadUrl == "https://example.com/upload-url-nullname"
    }
    
    // ==================================
    // createAttachment
    // ==================================

    def "createAttachmentはhouseholdが一致しない場合IllegalStateExceptionを投げる"() {
        given:
        Long shoppingItemId = 200L
        Long userId = 10L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> 1L
        }
        def otherHousehold = Mock(HouseholdModel) {
            getHouseholdId() >> 2L
        }

        when:
        service.createAttachment(shoppingItemId, "file-key", "file.png", "image/png", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [otherHousehold]
        0 * attachmentRepository.findByShoppingItemId(_)
        0 * attachmentRepository.save(_, _, _)

        thrown(IllegalStateException)
    }

    def "createAttachmentは既存添付の最大sortOrderに1を足した値で保存する"() {
        given:
        Long shoppingItemId = 201L
        Long userId = 10L
        Long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        // 既に sortOrder=1 の添付がある想定 → 次は2になる
        def existing = ShoppingItemAttachment.reconstruct(
                1L, shoppingItemId, "key1", "file1.png", "image/png", 1
        )

        when:
        def result = service.createAttachment(shoppingItemId, "new-key", "new.png", "image/png", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]

        1 * attachmentRepository.findByShoppingItemId(shoppingItemId) >> [existing]

        1 * attachmentRepository.save(_, userId, ProgramType.ONL_SHPATCH.code) >> { args ->
            def model = args[0] as ShoppingItemAttachment
            assert model.shoppingItemId == shoppingItemId
            assert model.fileKey == "new-key"
            assert model.fileName == "new.png"
            assert model.mimeType == "image/png"
            assert model.sortOrder == 2   // max(1) + 1
            return model
        }

        and:
        result.shoppingItemId == shoppingItemId
        result.sortOrder == 2
    }

    def "createAttachmentは添付が存在しない場合sortOrderは1になる"() {
        given:
        Long shoppingItemId = 202L
        Long userId = 10L
        Long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        when:
        def result = service.createAttachment(shoppingItemId, "key", "file.png", "image/png", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]

        1 * attachmentRepository.findByShoppingItemId(shoppingItemId) >> []

        1 * attachmentRepository.save(_, userId, ProgramType.ONL_SHPATCH.code) >> { args ->
            def model = args[0] as ShoppingItemAttachment
            assert model.sortOrder == 1
            return model
        }

        and:
        result.sortOrder == 1
    }

    // ==================================
    // listAttachments
    // ==================================

    def "listAttachmentsはhouseholdが一致しない場合IllegalStateExceptionを投げる"() {
        given:
        Long shoppingItemId = 300L
        Long userId = 10L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> 1L
        }
        def otherHousehold = Mock(HouseholdModel) {
            getHouseholdId() >> 2L
        }

        when:
        service.listAttachments(shoppingItemId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [otherHousehold]
        0 * attachmentRepository.findByShoppingItemId(_)
        thrown(IllegalStateException)
    }

    def "listAttachmentsは認可OKならpresigned GET URLを設定して返す"() {
        given:
        Long shoppingItemId = 301L
        Long userId = 10L
        Long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        def a1 = ShoppingItemAttachment.reconstruct(
                1L, shoppingItemId, "key1", "file1.png", "image/png", 1
        )
        def a2 = ShoppingItemAttachment.reconstruct(
                2L, shoppingItemId, "key2", "file2.png", "image/png", 2
        )

        when:
        def result = service.listAttachments(shoppingItemId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]

        1 * attachmentRepository.findByShoppingItemId(shoppingItemId) >> [a1, a2]

        1 * objectStorageClient.createPresignedGetUrl("bucket-name", "key1", _ as Duration) >> new URL("https://example.com/img1")
        1 * objectStorageClient.createPresignedGetUrl("bucket-name", "key2", _ as Duration) >> new URL("https://example.com/img2")

        and:
        result.size() == 2
        result[0].imageUrl == "https://example.com/img1"
        result[1].imageUrl == "https://example.com/img2"
    }

    // ==================================
    // deleteAttachment
    // ==================================

    def "deleteAttachmentはhouseholdが一致しない場合IllegalStateExceptionを投げる"() {
        given:
        Long shoppingItemId = 400L
        Long attachmentId = 1L
        Long userId = 10L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> 1L
        }
        def otherHousehold = Mock(HouseholdModel) {
            getHouseholdId() >> 2L
        }

        when:
        service.deleteAttachment(shoppingItemId, attachmentId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [otherHousehold]
        0 * attachmentRepository.findById(_)
        0 * attachmentRepository.deleteById(_)
        thrown(IllegalStateException)
    }

    def "deleteAttachmentはattachmentのshoppingItemIdが一致しない場合IllegalStateExceptionを投げる"() {
        given:
        Long shoppingItemId = 401L
        Long attachmentId = 1L
        Long userId = 10L
        Long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        def attachment = ShoppingItemAttachment.reconstruct(
                attachmentId, 999L, "key", "file.png", "image/png", 1
        )

        when:
        service.deleteAttachment(shoppingItemId, attachmentId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]
        1 * attachmentRepository.findById(attachmentId) >> Optional.of(attachment)
        0 * attachmentRepository.deleteById(_)

        thrown(IllegalStateException)
    }

    def "deleteAttachmentは認可OKかつshoppingItemId一致のとき削除する"() {
        given:
        Long shoppingItemId = 402L
        Long attachmentId = 1L
        Long userId = 10L
        Long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def household = Mock(HouseholdModel) {
            getHouseholdId() >> householdId
        }

        def attachment = ShoppingItemAttachment.reconstruct(
                attachmentId, shoppingItemId, "key", "file.png", "image/png", 1
        )

        when:
        service.deleteAttachment(shoppingItemId, attachmentId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * userService.getHouseholds(userId) >> [household]
        1 * attachmentRepository.findById(attachmentId) >> Optional.of(attachment)
        1 * attachmentRepository.deleteById(attachmentId)
        0 * objectStorageClient._   // S3削除は行わない前提
    }
}
