package com.hwhub.backend.presentation.rest.shopping

import com.hwhub.backend.application.service.ShoppingItemService
import com.hwhub.backend.domain.enums.FavoriteFlag
import com.hwhub.backend.domain.enums.ShoppingItemStatus
import com.hwhub.backend.domain.model.ShoppingItemModel
import com.hwhub.backend.presentation.rest.shopping.dto.CreateShoppingItemRequest
import com.hwhub.backend.presentation.rest.shopping.dto.ShoppingItemDto
import com.hwhub.backend.presentation.rest.shopping.dto.ShoppingItemListResponse
import com.hwhub.backend.presentation.rest.shopping.dto.BulkUpdateStatusRequest
import com.hwhub.backend.presentation.rest.shopping.dto.UpdateFavoriteRequest
import com.hwhub.backend.presentation.rest.shopping.dto.UpdateShoppingItemRequest
import com.hwhub.backend.presentation.rest.shopping.dto.UpdateStatusRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class ShoppingItemControllerSpec extends Specification {

    ShoppingItemService shoppingItemService = Mock()
    ShoppingItemController controller = new ShoppingItemController(shoppingItemService)

    // ------------------------------------------------------------------
    // GET /api/households/{householdId}/shopping-items
    // ------------------------------------------------------------------
    def "getShoppingItems は householdId と認証ユーザIDで service.getShoppingItems を呼びレスポンスを返す"() {
        given:
        Long householdId = 10L
        Long userId = 20L

        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        def model1 = ShoppingItemModel.reconstruct(
                1L, householdId, "牛乳", "メモ1", "1",
                ShoppingItemStatus.NOT_PURCHASED.code,
                FavoriteFlag.NORMAL.code,
                null,
                LocalDateTime.now(),
                false
        )
        def model2 = ShoppingItemModel.reconstruct(
                2L, householdId, "パン", "メモ2", "2",
                ShoppingItemStatus.IN_BASKET.code,
                FavoriteFlag.FAVORITE.code,
                null,
                LocalDateTime.now(),
                true
        )

        when:
        ShoppingItemListResponse response = controller.getShoppingItems(householdId, auth)

        then:
        1 * shoppingItemService.getShoppingItems(householdId, userId) >> [model1, model2]

        and:
        response != null

        response.items.size() == 2
        response.items.get(0).shoppingItemId == 1L
        response.items.get(1).shoppingItemId == 2L
    }

    // ------------------------------------------------------------------
    // GET /api/households/{householdId}/shopping-items/favorites
    // ------------------------------------------------------------------
    def "getFavoriteShoppingItems は householdId と認証ユーザIDで service.getFavoriteShoppingItems を呼びレスポンスを返す"() {
        given:
        Long householdId = 10L
        Long userId = 20L

        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        def model1 = ShoppingItemModel.reconstruct(
                1L, householdId, "牛乳", "メモ1", "1",
                ShoppingItemStatus.NOT_PURCHASED.code,
                FavoriteFlag.FAVORITE.code,
                null,
                LocalDateTime.now(),
                false
        )

        when:
        ShoppingItemListResponse response = controller.getFavorites(householdId, auth)

        then:
        1 * shoppingItemService.getFavoriteShoppingItems(householdId, userId) >> [model1]

        and:
        response != null
        response.items.size() == 1
        response.items.get(0).shoppingItemId == 1L
        response.items.get(0).name == "牛乳"
    }

    // ------------------------------------------------------------------
    // PATCH /api/shopping-items/{shoppingItemId}/favorite
    // ------------------------------------------------------------------
    def "updateFavorite は ログインユーザIDとリクエストのfavoriteを使って service.updateFavorite を呼び 204 を返す"() {
        given:
        Long shoppingItemId = 100L
        long userId = 30L
        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        // UpdateFavoriteRequest は record UpdateFavoriteRequest(String favorite) を想定
        def request = new UpdateFavoriteRequest(FavoriteFlag.FAVORITE.code)

        when:
        def response = controller.updateFavorite(shoppingItemId, request, auth)

        then:
        1 * shoppingItemService.updateFavorite(shoppingItemId, FavoriteFlag.FAVORITE.code, userId)

        and:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    // ------------------------------------------------------------------
    // PATCH /api/shopping-items/bulk-status
    // ------------------------------------------------------------------
    def "bulkUpdateStatus は リクエストのids・statusとログインユーザIDで service.bulkUpdateStatus を呼び 204 を返す"() {
        given:
        long userId = 50L
        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        def ids = [1L, 2L, 3L]
        def request = new BulkUpdateStatusRequest(ids, ShoppingItemStatus.PURCHASED.code)

        when:
        def response = controller.bulkUpdateStatus(request, auth)

        then:
        1 * shoppingItemService.bulkUpdateStatus(ids, ShoppingItemStatus.PURCHASED.code, userId)

        and:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    // ------------------------------------------------------------------
    // PATCH /api/shopping-items/{shoppingItemId}/status
    // ------------------------------------------------------------------
    def "updateStatus は ログインユーザIDとリクエストのstatusを使って service.updateStatus を呼び 204 を返す"() {
        given:
        Long shoppingItemId = 200L
        long userId = 40L
        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        // UpdateStatusRequest は record UpdateStatusRequest(String status) を想定
        def request = new UpdateStatusRequest(ShoppingItemStatus.PURCHASED.code)

        when:
        def response = controller.updateStatus(shoppingItemId, request, auth)

        then:
        1 * shoppingItemService.updateStatus(shoppingItemId, ShoppingItemStatus.PURCHASED.code, userId)

        and:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    // ------------------------------------------------------------------
    // POST /api/households/{householdId}/shopping-items
    // ------------------------------------------------------------------
    def "create は householdId とリクエスト内容から ShoppingItemModel.create を組み立てて service.create を呼び 201 を返す"() {
        given:
        Long householdId = 50L
        long userId = 60L
        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        // CreateShoppingItemRequest は Javaクラス想定（デフォルトコンストラクタ + プロパティ）
        def request = new CreateShoppingItemRequest()
        request.setName("卵")
        request.setMemo("10個入り")
        request.setStoreType("1")
        request.setSourceShoppingItemId(999L)

        when:
        def response = controller.create(householdId, request, auth)

        then:
        1 * shoppingItemService.create(_, 999L, userId) >> { ShoppingItemModel m, Long srcId, Long uid ->
            assert m.householdId == householdId
            assert m.name == "卵"
            assert m.memo == "10個入り"
            assert m.storeType == "1"
            // create() の初期値も軽く確認
            assert m.status == ShoppingItemStatus.NOT_PURCHASED.code
            assert m.favorite == FavoriteFlag.NORMAL.code

            return ShoppingItemModel.reconstruct(
                    123L,
                    m.householdId,
                    m.name,
                    m.memo,
                    m.storeType,
                    m.status,
                    m.favorite,
                    null,
                    LocalDateTime.now(),
                    false
            )
        }

        and:
        response.statusCode == HttpStatus.CREATED
        ShoppingItemDto body = response.body
        body != null
        body.shoppingItemId == 123L
        body.name == "卵"
    }

    // ------------------------------------------------------------------
    // DELETE /api/shopping-items/{shoppingItemId}
    // ------------------------------------------------------------------
    def "delete は ログインユーザIDで service.delete を呼び 204 を返す"() {
        given:
        Long shoppingItemId = 300L
        long userId = 50L
        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        when:
        def response = controller.delete(shoppingItemId, auth)

        then:
        1 * shoppingItemService.delete(shoppingItemId, userId)

        and:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    // ------------------------------------------------------------------
    // PUT /api/households/{householdId}/shopping-items/{shoppingItemId}
    // ------------------------------------------------------------------
    def "update は householdId, shoppingItemId, リクエスト内容, ログインユーザIDで service.update を呼び 200 を返す"() {
        given:
        Long householdId = 70L
        Long shoppingItemId = 80L
        long userId = 90L

        Authentication auth = Mock()
        auth.getName() >> userId.toString()

        // UpdateShoppingItemRequest は record UpdateShoppingItemRequest(String name, String memo, String storeType) を想定
        def request = new UpdateShoppingItemRequest("牛乳", "成分無調整", "2", "0")

        when:
        def response = controller.update(householdId, shoppingItemId, request, auth)

        then:
        1 * shoppingItemService.update(
                householdId,
                shoppingItemId,
                "牛乳",
                "成分無調整",
                "2",
                userId
        ) >> ShoppingItemModel.reconstruct(
                shoppingItemId,
                householdId,
                "牛乳",
                "成分無調整",
                "2",
                ShoppingItemStatus.IN_BASKET.code,
                FavoriteFlag.NORMAL.code,
                LocalDate.now(),
                LocalDateTime.now(),
                false
        )

        and:
        response.statusCode == HttpStatus.OK
        def body = response.body
        body != null
        body.shoppingItemId == shoppingItemId
        body.name == "牛乳"
    }
}
