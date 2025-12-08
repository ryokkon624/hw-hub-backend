package com.hwhub.backend.presentation.rest.shopping.history

import com.hwhub.backend.application.service.ShoppingItemService
import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel
import com.hwhub.backend.presentation.rest.shopping.history.dto.ShoppingItemHistorySuggestionResponse
import org.springframework.security.core.Authentication
import spock.lang.Specification

import java.time.LocalDate

class ShoppingItemHistoryControllerSpec extends Specification {

    ShoppingItemService shoppingItemService = Mock()
    ShoppingItemHistoryController controller =
            new ShoppingItemHistoryController(shoppingItemService)

    def "listHistorySuggestions は service.listHistorySuggestions の結果をレスポンスDTOにマッピングして返す"() {
        given:
        Long householdId = 10L
        long userId = 20L
        String keyword = "milk"
        String storeTypeParam = "1"
        int limit = 30

        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        def m1 = Mock(ShoppingItemHistorySuggestionModel) {
            getName() >> "牛乳"
            getMemo() >> "低脂肪"
            getStoreType() >> "1"
            getLastPurchasedDate() >> LocalDate.of(2025, 1, 1)
            getPurchaseCount() >> 3L
            getSourceShoppingItemId() >> 100L
            getFavorite() >> "1"
        }

        def m2 = Mock(ShoppingItemHistorySuggestionModel) {
            getName() >> "パン"
            getMemo() >> "食パン"
            getStoreType() >> "2"
            getLastPurchasedDate() >> LocalDate.of(2025, 2, 1)
            getPurchaseCount() >> 5L
            getSourceShoppingItemId() >> 101L
            getFavorite() >> "0"
        }

        when:
        List<ShoppingItemHistorySuggestionResponse> result =
                controller.listHistorySuggestions(householdId, keyword, storeTypeParam, limit, auth)

        then:
        1 * shoppingItemService.listHistorySuggestions(
                householdId,
                userId,
                keyword,
                storeTypeParam,
                limit
        ) >> [m1, m2]

        and:
        result.size() == 2

        with(result[0]) {
            name() == "牛乳"
            memo() == "低脂肪"
            storeType() == "1"
            lastPurchasedDate() == LocalDate.of(2025, 1, 1)
            purchaseCount() == 3L
            sourceShoppingItemId() == 100L
            favorite() == "1"
        }
        with(result[1]) {
            name() == "パン"
            memo() == "食パン"
            storeType() == "2"
            lastPurchasedDate() == LocalDate.of(2025, 2, 1)
            purchaseCount() == 5L
            sourceShoppingItemId() == 101L
            favorite() == "0"
        }
    }

    def "listHistorySuggestions は keyword, storeType, limitがnull/デフォルトでもそのまま引き渡す"() {
        given:
        Long householdId = 99L
        long userId = 30L
        // keyword = null, storeType = null, limit = デフォルト(20)
        Authentication auth = Mock()
        auth.getName() >> String.valueOf(userId)

        when:
        List<ShoppingItemHistorySuggestionResponse> result =
                controller.listHistorySuggestions(householdId, null, null, 20, auth)

        then:
        1 * shoppingItemService.listHistorySuggestions(
                householdId,
                userId,
                null,
                null,
                20
        ) >> []

        and:
        result.isEmpty()
    }
}
