package com.hwhub.backend.domain.model

import spock.lang.Specification

class ShoppingItemAttachmentSpec extends Specification {

    def "reconstructは全フィールドを正しく復元する"() {
        when:
        def model = ShoppingItemAttachment.reconstruct(
                1L,
                10L,
                "file-key",
                "photo.png",
                "image/png",
                3
        )

        then:
        model.id == 1L
        model.shoppingItemId == 10L
        model.fileKey == "file-key"
        model.fileName == "photo.png"
        model.mimeType == "image/png"
        model.sortOrder == 3
        model.imageUrl == null   // reconstructはimageUrlを持たない
    }

    def "createはid=nullで新規インスタンスを生成する"() {
        when:
        def model = ShoppingItemAttachment.create(
                20L,
                "new-key",
                "new.jpg",
                "image/jpeg",
                1
        )

        then:
        model.id == null
        model.shoppingItemId == 20L
        model.fileKey == "new-key"
        model.fileName == "new.jpg"
        model.mimeType == "image/jpeg"
        model.sortOrder == 1
        model.imageUrl == null
    }

    def "setImageUrlでimageUrlが更新される"() {
        given:
        def model = ShoppingItemAttachment.create(
                30L,
                "key",
                "file.png",
                "image/png",
                2
        )

        when:
        model.setImageUrl("https://example.com/image.png")

        then:
        model.imageUrl == "https://example.com/image.png"
    }
}
