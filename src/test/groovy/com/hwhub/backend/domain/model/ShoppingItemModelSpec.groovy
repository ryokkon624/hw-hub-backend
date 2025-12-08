package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.FavoriteFlag
import com.hwhub.backend.domain.enums.ShoppingItemStatus
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class ShoppingItemModelSpec extends Specification {

    def "reconstructは全フィールドを正しく復元する"() {
        given:
        Long shoppingItemId = 1L
        Long householdId = 10L
        String name = "牛乳"
        String memo = "低脂肪を買う"
        String storeType = "SUPERMARKET"
        String status = ShoppingItemStatus.PURCHASED.code
        String favorite = FavoriteFlag.FAVORITE.code
        LocalDate purchasedAt = LocalDate.of(2025, 1, 2)
        LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 10, 0)
        Boolean hasImage = true

        when:
        def model = ShoppingItemModel.reconstruct(
                shoppingItemId,
                householdId,
                name,
                memo,
                storeType,
                status,
                favorite,
                purchasedAt,
                createdAt,
                hasImage
        )

        then:
        model.shoppingItemId == shoppingItemId
        model.householdId == householdId
        model.name == name
        model.memo == memo
        model.storeType == storeType
        model.status == status
        model.favorite == favorite
        model.purchasedAt == purchasedAt
        model.createdAt == createdAt
        model.hasImage == hasImage
    }

    def "createはNOT_PURCHASEDとNORMALお気に入りで新規インスタンスを生成する"() {
        given:
        Long householdId = 10L
        String name = "卵"
        String memo = "10個入り"
        String storeType = "SUPERMARKET"

        when:
        def model = ShoppingItemModel.create(householdId, name, memo, storeType)

        then: "IDはnullで生成される"
        model.shoppingItemId == null

        and: "入力値がそのままセットされる"
        model.householdId == householdId
        model.name == name
        model.memo == memo
        model.storeType == storeType

        and: "ステータスはNOT_PURCHASEDで、お気に入りはNORMAL"
        model.status == ShoppingItemStatus.NOT_PURCHASED.code
        model.favorite == FavoriteFlag.NORMAL.code

        and: "購入日はnull、その他は初期値"
        model.purchasedAt == null
        model.createdAt == null
        model.hasImage == false
    }

    def "notPurcasedでステータスがNOT_PURCHASEDになりpurchasedAtがnullになる"() {
        given:
        def model = ShoppingItemModel.reconstruct(
                1L,
                10L,
                "パン",
                "食パン",
                "SUPERMARKET",
                ShoppingItemStatus.PURCHASED.code,
                FavoriteFlag.NORMAL.code,
                LocalDate.of(2025, 1, 5),
                LocalDateTime.now(),
                false
        )

        when:
        model.notPurcased()

        then:
        model.status == ShoppingItemStatus.NOT_PURCHASED.code
        model.purchasedAt == null
    }

    def "inBasketでステータスがIN_BASKETになりpurchasedAtがnullになる"() {
        given:
        def model = ShoppingItemModel.reconstruct(
                1L,
                10L,
                "パン",
                "食パン",
                "SUPERMARKET",
                ShoppingItemStatus.PURCHASED.code,
                FavoriteFlag.NORMAL.code,
                LocalDate.of(2025, 1, 5),
                LocalDateTime.now(),
                false
        )

        when:
        model.inBasket()

        then:
        model.status == ShoppingItemStatus.IN_BASKET.code
        model.purchasedAt == null
    }

    def "purchasedでステータスがPURCHASEDになり本日の日付でpurchasedAtが設定される"() {
        given:
        def model = ShoppingItemModel.reconstruct(
                1L,
                10L,
                "パン",
                "食パン",
                "SUPERMARKET",
                ShoppingItemStatus.NOT_PURCHASED.code,
                FavoriteFlag.NORMAL.code,
                null,
                LocalDateTime.now(),
                false
        )
        def today = LocalDate.now()

        when:
        model.purchased()

        then:
        model.status == ShoppingItemStatus.PURCHASED.code
        model.purchasedAt == today
    }

    def "favoriteでお気に入りがFAVORITEになりclearFavoriteでNORMALに戻る"() {
        given:
        def model = ShoppingItemModel.reconstruct(
                1L,
                10L,
                "コーヒー豆",
                "深煎り",
                "ONLINE",
                ShoppingItemStatus.NOT_PURCHASED.code,
                FavoriteFlag.NORMAL.code,
                null,
                LocalDateTime.now(),
                false
        )

        when: "favoriteを呼ぶ"
        model.favorite()

        then:
        model.favorite == FavoriteFlag.FAVORITE.code

        when: "clearFavoriteを呼ぶ"
        model.clearFavorite()

        then:
        model.favorite == FavoriteFlag.NORMAL.code
    }

    def "updateでname, memo, storeTypeが更新される"() {
        given:
        def model = ShoppingItemModel.reconstruct(
                1L,
                10L,
                "古い名前",
                "古いメモ",
                "OLD_STORE",
                ShoppingItemStatus.NOT_PURCHASED.code,
                FavoriteFlag.NORMAL.code,
                null,
                LocalDateTime.now(),
                false
        )

        when:
        model.update("新しい名前", "新しいメモ", "NEW_STORE")

        then:
        model.name == "新しい名前"
        model.memo == "新しいメモ"
        model.storeType == "NEW_STORE"
    }
}
