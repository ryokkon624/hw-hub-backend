package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.ShoppingItemAttachment
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemAttachment
import spock.lang.Specification

class ShoppingItemAttachmentConverterSpec extends Specification{

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        ShoppingItemAttachmentConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "reconstructで生成されたShoppingItemAttachmentモデル"
        def model = ShoppingItemAttachment.reconstruct(
                1L,                  // id
                10L,                 // shoppingItemId
                "file/key/001",      // fileKey
                "receipt.png",       // fileName
                "image/png",         // mimeType
                5                    // sortOrder
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = ShoppingItemAttachmentConverter.toEntity(model)

        then: "エンティティが生成され、対応するフィールドがコピーされている"
        entity != null
        with(entity) {
            shoppingItemAttachmentId == 1L
            shoppingItemId == 10L
            fileKey == "file/key/001"
            fileName == "receipt.png"
            mimeType == "image/png"
            sortOrder == 5
        }
    }

    def "toModelは引数がnullのときnullを返す"() {
        expect:
        ShoppingItemAttachmentConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ全フィールドを変換しimageUrlはnullのままになる"() {
        given: "すべてのフィールドがセットされたTShoppingItemAttachmentエンティティ"
        def entity = new TShoppingItemAttachment()
        entity.setShoppingItemAttachmentId(2L)
        entity.setShoppingItemId(20L)
        entity.setFileKey("file/key/002")
        entity.setFileName("manual.pdf")
        entity.setMimeType("application/pdf")
        entity.setSortOrder(3)

        when: "toModelでドメインモデルに変換する"
        def model = ShoppingItemAttachmentConverter.toModel(entity)

        then: "IDやファイル情報が正しくコピーされている"
        model != null
        with(model) {
            id == 2L
            shoppingItemId == 20L
            fileKey == "file/key/002"
            fileName == "manual.pdf"
            mimeType == "application/pdf"
            sortOrder == 3
        }

        and: "imageUrlはreconstructの仕様どおりnullのままである"
        model.imageUrl == null
    }
}
