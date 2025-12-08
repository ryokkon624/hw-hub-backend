package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.ShoppingItemModel
import com.hwhub.backend.infrastructure.mybatis.custom.entity.ShoppingItemWithImageEntity
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItem
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class ShoppingItemConverterSpec extends Specification{

    def "toModel(ShoppingItemWithImageEntity)は引数がnullのときnullを返す"() {
        expect:
        ShoppingItemConverter.toModel((ShoppingItemWithImageEntity) null) == null
    }

    def "toModel(ShoppingItemWithImageEntity)は画像有無フラグ含めて全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたShoppingItemWithImageEntity"
        LocalDate purchasedAt = LocalDate.of(2025, 1, 10)
        LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 9, 0, 0)

        def entity = new ShoppingItemWithImageEntity()
        entity.setShoppingItemId(1L)
        entity.setHouseholdId(10L)
        entity.setName("牛乳")
        entity.setMemo("成分無調整")
        entity.setStoreType("SUPERMARKET")
        entity.setStatus("0")
        entity.setFavorite("0")
        entity.setPurchasedAt(DateConverter.toDate(purchasedAt))
        entity.setCreatedAt(DateConverter.toDate(createdAt))
        entity.setHasImage(true)

        when: "toModel(ShoppingItemWithImageEntity)でドメインモデルに変換する"
        def model = ShoppingItemConverter.toModel(entity)

        then: "基本情報・ステータス・日付が正しくコピーされている"
        model != null
        with(model) {
            shoppingItemId == 1L
            householdId == 10L
            name == "牛乳"
            memo == "成分無調整"
            storeType == "SUPERMARKET"
            status == "0"
            favorite == "0"
            purchasedAt == purchasedAt
            createdAt == createdAt
            hasImage == true
        }
    }

    def "toModel(TShoppingItem)は引数がnullのときnullを返す"() {
        expect:
        ShoppingItemConverter.toModel((TShoppingItem) null) == null
    }

    def "toModel(TShoppingItem)はhasImageをfalseに固定して変換する"() {
        given: "TShoppingItemエンティティのみが存在する（画像有無情報は持たない）"
        LocalDate purchasedAt = LocalDate.of(2025, 2, 20)
        LocalDateTime createdAt = LocalDateTime.of(2025, 2, 1, 8, 30, 0)

        def entity = new TShoppingItem()
        entity.setShoppingItemId(2L)
        entity.setHouseholdId(20L)
        entity.setName("パン")
        entity.setMemo("食パン")
        entity.setStoreType("BAKERY")
        entity.setStatus("1")
        entity.setFavorite("1")
        entity.setPurchasedAt(DateConverter.toDate(purchasedAt))
        entity.setCreatedAt(DateConverter.toDate(createdAt))

        when: "toModel(TShoppingItem)でドメインモデルに変換する"
        def model = ShoppingItemConverter.toModel(entity)

        then: "基本情報・ステータス・日付はコピーされ、hasImageはfalseになる"
        model != null
        with(model) {
            shoppingItemId == 2L
            householdId == 20L
            name == "パン"
            memo == "食パン"
            storeType == "BAKERY"
            status == "1"
            favorite == "1"
            purchasedAt == purchasedAt
            createdAt == createdAt
            hasImage == false
        }
    }

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        ShoppingItemConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換するがcreatedAtはセットしない"() {
        given: "すべてのフィールドがセットされたShoppingItemModel"
        LocalDate purchasedAt = LocalDate.of(2025, 3, 15)
        LocalDateTime createdAt = LocalDateTime.of(2025, 3, 1, 7, 0, 0)

        def model = ShoppingItemModel.reconstruct(
                3L,                 // shoppingItemId
                30L,                // householdId
                "卵",               // name
                "10個入り",          // memo
                "DISCOUNT_STORE",   // storeType
                "2",                // status
                "1",                // favorite
                purchasedAt,        // purchasedAt
                createdAt,          // createdAt（toEntityでは使用しない）
                true                // hasImage（toEntityでは使用しない）
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = ShoppingItemConverter.toEntity(model)

        then: "IDや基本情報・ステータスが正しくコピーされている"
        entity != null
        with(entity) {
            shoppingItemId == 3L
            householdId == 30L
            name == "卵"
            memo == "10個入り"
            storeType == "DISCOUNT_STORE"
            status == "2"
            favorite == "1"
        }

        and: "購入日付はLocalDateと相互変換しても同じ値になる"
        DateConverter.toLocalDate(entity.purchasedAt) == purchasedAt

        and: "createdAtはconverterの仕様どおりnullのままである"
        entity.createdAt == null
    }
}
