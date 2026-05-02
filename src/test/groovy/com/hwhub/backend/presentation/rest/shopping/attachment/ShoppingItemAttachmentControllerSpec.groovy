package com.hwhub.backend.presentation.rest.shopping.attachment

import com.hwhub.backend.application.service.ShoppingItemAttachmentService
import com.hwhub.backend.domain.model.ShoppingItemAttachment
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.*
import spock.lang.Specification

class ShoppingItemAttachmentControllerSpec extends Specification {

    ShoppingItemAttachmentService attachmentService = Mock()
    ShoppingItemAttachmentController controller =
            new ShoppingItemAttachmentController(attachmentService)

    // --------------------------------------------------------
    // POST /api/shopping-items/{itemId}/attachments/upload-url
    // --------------------------------------------------------
    def "createUploadUrl は ログインユーザIDを使って service.createUploadUrl を呼び DTO を返す"() {
        given:
        Long itemId = 10L
        Long userId = 20L

        def request = new CreateUploadUrlRequest("photo.png", "image/png")

        when:
        CreateUploadUrlResponse response = controller.createUploadUrl(itemId, request, userId)

        then:
        1 * attachmentService.createUploadUrl(itemId, "photo.png", "image/png", userId) >>
                new ShoppingItemAttachmentService.CreateUploadUrlResult(
                        "https://example.com/put-url",
                        "shopping-item/10/999/uuid.png"
                )

        and:
        response != null
        response.uploadUrl() == "https://example.com/put-url"
        response.fileKey() == "shopping-item/10/999/uuid.png"
    }

    // --------------------------------------------------------
    // POST /api/shopping-items/{itemId}/attachments
    // --------------------------------------------------------
    def "createAttachment は service.createAttachment を呼び 戻り値のIDを CreateAttachmentResponse に詰めて返す"() {
        given:
        Long itemId = 11L
        Long userId = 30L

        def request = new CreateAttachmentRequest(
                "shopping-item/11/100/uuid.jpg",
                "image.jpg",
                "image/jpeg"
        )

        when:
        CreateAttachmentResponse response = controller.createAttachment(itemId, request, userId)

        then:
        1 * attachmentService.createAttachment(
                itemId,
                "shopping-item/11/100/uuid.jpg",
                "image.jpg",
                "image/jpeg",
                userId
        ) >> ShoppingItemAttachment.reconstruct(
                123L,
                itemId,
                "shopping-item/11/100/uuid.jpg",
                "image.jpg",
                "image/jpeg",
                1
        )

        and:
        response != null
        response.id() == 123L
    }

    // --------------------------------------------------------
    // GET /api/shopping-items/{itemId}/attachments
    // --------------------------------------------------------
    def "listAttachments は service.listAttachments の結果を ShoppingItemAttachmentResponse にマッピングして返す"() {
        given:
        Long itemId = 12L
        Long userId = 40L

        def a1 = ShoppingItemAttachment.reconstruct(
                1L, itemId, "key1", "file1.png", "image/png", 1
        )
        def a2 = ShoppingItemAttachment.reconstruct(
                2L, itemId, "key2", "file2.png", "image/png", 2
        )
        a1.setImageUrl("https://example.com/img1")
        a2.setImageUrl("https://example.com/img2")

        when:
        List<ShoppingItemAttachmentResponse> responses =
                controller.listAttachments(itemId, userId)

        then:
        1 * attachmentService.listAttachments(itemId, userId) >> [a1, a2]

        and:
        responses.size() == 2

        with(responses[0]) {
            id() == 1L
            fileName() == "file1.png"
            imageUrl() == "https://example.com/img1"
            sortOrder() == 1
        }
        with(responses[1]) {
            id() == 2L
            fileName() == "file2.png"
            imageUrl() == "https://example.com/img2"
            sortOrder() == 2
        }
    }

    // --------------------------------------------------------
    // DELETE /api/shopping-items/{itemId}/attachments/{attachmentId}
    // --------------------------------------------------------
    def "deleteAttachment は ログインユーザIDと共に service.deleteAttachment を呼ぶ"() {
        given:
        Long itemId = 13L
        Long attachmentId = 99L
        Long userId = 50L

        when:
        controller.deleteAttachment(itemId, attachmentId, userId)

        then:
        1 * attachmentService.deleteAttachment(itemId, attachmentId, userId)
    }
}
