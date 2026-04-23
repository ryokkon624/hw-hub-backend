package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.FavoriteFlag
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.ShoppingItemStatus
import com.hwhub.backend.domain.model.ShoppingItemAttachment
import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel
import com.hwhub.backend.domain.model.ShoppingItemModel
import com.hwhub.backend.domain.repository.ShoppingItemAttachmentRepository
import com.hwhub.backend.domain.repository.ShoppingItemRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import org.springframework.security.access.AccessDeniedException
import spock.lang.Specification

import java.util.Optional

class ShoppingItemServiceSpec extends Specification {

    ShoppingItemRepository shoppingItemRepository = Mock()
    HouseholdAuthorizationService householdAuthorizationService = Mock()
    ShoppingItemAttachmentRepository shoppingItemAttachmentRepository = Mock()

    ShoppingItemService service = new ShoppingItemService(
            shoppingItemRepository,
            householdAuthorizationService,
            shoppingItemAttachmentRepository
    )

    // ==================================
    // getShoppingItems
    // ==================================

    def "getShoppingItemsは認可OKなら世帯の買い物アイテム一覧を返す"() {
        given:
        long householdId = 1L
        long userId = 10L
        def items = [Mock(ShoppingItemModel), Mock(ShoppingItemModel)]

        when:
        def result = service.getShoppingItems(householdId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * shoppingItemRepository.findByHouseholdId(householdId) >> items

        and:
        result == items
    }

    def "getShoppingItemsは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long householdId = 1L
        long userId = 10L

        when:
        service.getShoppingItems(householdId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> false
        0 * shoppingItemRepository.findByHouseholdId(_)

        thrown(AccessDeniedException)
    }

    // ==================================
    // getFavoriteShoppingItems
    // ==================================

    def "getFavoriteShoppingItemsは認可OKなら世帯のお気に入りアイテム一覧を返す"() {
        given:
        long householdId = 1L
        long userId = 10L
        def items = [Mock(ShoppingItemModel), Mock(ShoppingItemModel)]

        when:
        def result = service.getFavoriteShoppingItems(householdId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * shoppingItemRepository.findFavoritesByHouseholdId(householdId) >> items

        and:
        result == items
    }

    def "getFavoriteShoppingItemsは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long householdId = 1L
        long userId = 10L

        when:
        service.getFavoriteShoppingItems(householdId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> false
        0 * shoppingItemRepository.findFavoritesByHouseholdId(_)

        thrown(AccessDeniedException)
    }

    // ==================================
    // updateFavorite
    // ==================================

    def "updateFavoriteはアイテムが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        long itemId = 100L
        long userId = 10L

        when:
        service.updateFavorite(itemId, FavoriteFlag.FAVORITE.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "updateFavoriteは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long itemId = 101L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateFavorite(itemId, FavoriteFlag.FAVORITE.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> false

        0 * item.favorite()
        0 * item.clearFavorite()
        0 * shoppingItemRepository.update(_, _, _)

        thrown(AccessDeniedException)
    }

    def "updateFavoriteはFAVORITE指定時にfavoriteを呼び出し更新する"() {
        given:
        long itemId = 102L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateFavorite(itemId, FavoriteFlag.FAVORITE.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * item.favorite()
        0 * item.clearFavorite()

        1 * shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.code)
    }

    def "updateFavoriteはFAVORITE以外指定時にclearFavoriteを呼び出し更新する"() {
        given:
        long itemId = 103L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateFavorite(itemId, FavoriteFlag.NORMAL.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        0 * item.favorite()
        1 * item.clearFavorite()

        1 * shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.code)
    }

    // ==================================
    // updateStatus
    // ==================================

    def "updateStatusはアイテムが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        long itemId = 200L
        long userId = 10L

        when:
        service.updateStatus(itemId, ShoppingItemStatus.NOT_PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "updateStatusは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long itemId = 201L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(itemId, ShoppingItemStatus.NOT_PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> false

        0 * item.notPurcased()
        0 * item.purchased()
        0 * item.inBasket()
        0 * shoppingItemRepository.update(_, _, _)

        thrown(AccessDeniedException)
    }

    def "updateStatusはNOT_PURCHASED指定時にnotPurcasedを呼び出し更新する"() {
        given:
        long itemId = 202L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(itemId, ShoppingItemStatus.NOT_PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * item.notPurcased()
        0 * item.purchased()
        0 * item.inBasket()

        1 * shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.code)
    }

    def "updateStatusはPURCHASED指定時にpurchasedを呼び出し更新する"() {
        given:
        long itemId = 203L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(itemId, ShoppingItemStatus.PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        0 * item.notPurcased()
        1 * item.purchased()
        0 * item.inBasket()

        1 * shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.code)
    }

    def "updateStatusはIN_BASKET指定時にinBasketを呼び出し更新する（default分岐）"() {
        given:
        long itemId = 204L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(itemId, ShoppingItemStatus.IN_BASKET.code, userId)

        then:
        1 * shoppingItemRepository.findById(itemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        0 * item.notPurcased()
        0 * item.purchased()
        1 * item.inBasket()

        1 * shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.code)
    }

    // ==================================
    // bulkUpdateStatus
    // ==================================

    def "bulkUpdateStatusはアイテムが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        long userId = 10L
        def ids = [700L]

        when:
        service.bulkUpdateStatus(ids, ShoppingItemStatus.PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(700L) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "bulkUpdateStatusは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long userId = 10L
        long householdId = 1L
        def ids = [701L]

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.bulkUpdateStatus(ids, ShoppingItemStatus.PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(701L) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> false
        0 * item.purchased()
        0 * shoppingItemRepository.update(_, _, _)

        thrown(AccessDeniedException)
    }

    def "bulkUpdateStatusは認可OKの場合全アイテムのステータスを一括更新する"() {
        given:
        long userId = 10L
        long householdId = 1L
        def ids = [702L, 703L]

        def item1 = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def item2 = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.bulkUpdateStatus(ids, ShoppingItemStatus.PURCHASED.code, userId)

        then:
        1 * shoppingItemRepository.findById(702L) >> Optional.of(item1)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * item1.purchased()
        1 * shoppingItemRepository.update(item1, userId, ProgramType.ONL_SHP.code)

        then:
        1 * shoppingItemRepository.findById(703L) >> Optional.of(item2)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * item2.purchased()
        1 * shoppingItemRepository.update(item2, userId, ProgramType.ONL_SHP.code)
    }

    // ==================================
    // create
    // ==================================

    def "createは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long userId = 10L
        def model = Mock(ShoppingItemModel) {
            getHouseholdId() >> 1L
        }

        when:
        service.create(model, null, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(1L, userId) >> false
        0 * shoppingItemRepository.insert(_, _, _)

        thrown(AccessDeniedException)
    }

    def "createはsourceShoppingItemIdがnullのとき本体だけ作成して返す"() {
        given:
        long userId = 10L
        long householdId = 1L

        def input = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def saved = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        def result = service.create(input, null, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * shoppingItemRepository.insert(input, userId, ProgramType.ONL_SHP.code) >> saved

        0 * shoppingItemRepository.findById(_)
        0 * shoppingItemAttachmentRepository.findByShoppingItemId(_)
        0 * shoppingItemAttachmentRepository.save(_, _, _)

        and:
        result == saved
    }

    def "createはコピー元が見つからない場合でも本体だけ返す"() {
        given:
        long userId = 10L
        long householdId = 1L
        long sourceId = 200L

        def input = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def saved = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
            getShoppingItemId() >> 300L
        }

        when:
        def result = service.create(input, sourceId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * shoppingItemRepository.insert(input, userId, ProgramType.ONL_SHP.code) >> saved
        1 * shoppingItemRepository.findById(sourceId) >> Optional.empty()

        0 * shoppingItemAttachmentRepository.findByShoppingItemId(_)
        0 * shoppingItemAttachmentRepository.save(_, _, _)

        and:
        result == saved
    }

    def "createはコピー元のhouseholdが異なる場合は添付コピーをスキップする"() {
        given:
        long userId = 10L
        long householdId = 1L
        long sourceId = 201L

        def input = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def saved = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
            getShoppingItemId() >> 300L
        }
        def source = Mock(ShoppingItemModel) {
            getHouseholdId() >> 999L
        }

        when:
        def result = service.create(input, sourceId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * shoppingItemRepository.insert(input, userId, ProgramType.ONL_SHP.code) >> saved
        1 * shoppingItemRepository.findById(sourceId) >> Optional.of(source)

        0 * shoppingItemAttachmentRepository.findByShoppingItemId(_)
        0 * shoppingItemAttachmentRepository.save(_, _, _)

        and:
        result == saved
    }

    def "createはコピー元と同じhouseholdかつ添付がある場合それらを新アイテムに連番sortOrderでコピーする"() {
        given:
        long userId = 10L
        long householdId = 1L
        long sourceId = 202L
        long newItemId = 300L

        def input = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def saved = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
            getShoppingItemId() >> newItemId
        }
        def source = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        def src1 = ShoppingItemAttachment.reconstruct(
                1L, sourceId, "key1", "f1.png", "image/png", 1
        )
        def src2 = ShoppingItemAttachment.reconstruct(
                2L, sourceId, "key2", "f2.png", "image/png", 2
        )

        when:
        def result = service.create(input, sourceId, userId)

        then:
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true
        1 * shoppingItemRepository.insert(input, userId, ProgramType.ONL_SHP.code) >> saved
        1 * shoppingItemRepository.findById(sourceId) >> Optional.of(source)
        1 * shoppingItemAttachmentRepository.findByShoppingItemId(sourceId) >> [src1, src2]

        1 * shoppingItemAttachmentRepository.save(_, userId, ProgramType.ONL_SHP.code) >> { args ->
            def a = args[0] as ShoppingItemAttachment
            assert a.shoppingItemId == newItemId
            assert a.fileKey == "key1"
            assert a.sortOrder == 1
            a
        }
        1 * shoppingItemAttachmentRepository.save(_, userId, ProgramType.ONL_SHP.code) >> { args ->
            def a = args[0] as ShoppingItemAttachment
            assert a.shoppingItemId == newItemId
            assert a.fileKey == "key2"
            assert a.sortOrder == 2
            a
        }

        and:
        result == saved
    }

    // ==================================
    // update
    // ==================================

    def "updateはアイテムが存在しない場合IllegalArgumentExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long shoppingItemId = 500L
        Long userId = 10L

        when:
        service.update(householdId, shoppingItemId, "name", "memo", "store", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.empty()
        thrown(IllegalArgumentException)
    }

    def "updateはhouseholdIdが一致しない場合IllegalStateExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long shoppingItemId = 501L
        Long userId = 10L

        def model = Mock(ShoppingItemModel) {
            getHouseholdId() >> 999L
        }

        when:
        service.update(householdId, shoppingItemId, "name", "memo", "store", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(model)

        0 * householdAuthorizationService.assertUserBelongsToHousehold(_, _)
        0 * shoppingItemRepository.update(_, _, _)

        thrown(IllegalStateException)
    }

    def "updateは正しいhouseholdかつ認可OKならupdateを呼び出し更新結果を返す"() {
        given:
        Long householdId = 1L
        Long shoppingItemId = 502L
        Long userId = 10L

        def model = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }
        def updated = Mock(ShoppingItemModel)

        when:
        def result = service.update(householdId, shoppingItemId, "newName", "newMemo", "newStore", userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(model)
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)

        1 * model.update("newName", "newMemo", "newStore")
        1 * shoppingItemRepository.update(model, userId, ProgramType.ONL_SHP.code) >> updated

        and:
        result == updated
    }

    // ==================================
    // delete
    // ==================================

    def "deleteはアイテムが存在しない場合ResourceNotFoundExceptionを投げる"() {
        given:
        long shoppingItemId = 600L
        long userId = 10L

        when:
        service.delete(shoppingItemId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.empty()
        0 * shoppingItemAttachmentRepository.deleteByShoppingItemId(_)
        0 * shoppingItemRepository.deleteById(_)

        thrown(ResourceNotFoundException)
    }

    def "deleteは世帯アクセス不可の場合AccessDeniedExceptionを投げる"() {
        given:
        long shoppingItemId = 601L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.delete(shoppingItemId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> false
        0 * shoppingItemAttachmentRepository.deleteByShoppingItemId(_)
        0 * shoppingItemRepository.deleteById(_)

        thrown(AccessDeniedException)
    }

    def "deleteは認可OKの場合添付画像を先に削除してからアイテムを削除する"() {
        given:
        long shoppingItemId = 602L
        long userId = 10L
        long householdId = 1L

        def item = Mock(ShoppingItemModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.delete(shoppingItemId, userId)

        then:
        1 * shoppingItemRepository.findById(shoppingItemId) >> Optional.of(item)
        1 * householdAuthorizationService.canAccessHousehold(householdId, userId) >> true

        then:
        1 * shoppingItemAttachmentRepository.deleteByShoppingItemId(shoppingItemId)

        then:
        1 * shoppingItemRepository.deleteById(shoppingItemId)
    }

    // ==================================
    // listHistorySuggestions
    // ==================================

    def "listHistorySuggestionsは認可チェック後にlimitが0以下なら20件で検索する"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String keyword = "milk"
        String storeType = "SUPERMARKET"
        int limit = 0

        def suggestions = [Mock(ShoppingItemHistorySuggestionModel)]

        when:
        def result = service.listHistorySuggestions(householdId, userId, keyword, storeType, limit)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * shoppingItemRepository.findHistorySuggestions(householdId, keyword, storeType, 20) >> suggestions

        and:
        result == suggestions
    }

    def "listHistorySuggestionsはlimitが101以上なら20件で検索する"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String keyword = "milk"
        String storeType = "SUPERMARKET"
        int limit = 200

        def suggestions = [Mock(ShoppingItemHistorySuggestionModel)]

        when:
        def result = service.listHistorySuggestions(householdId, userId, keyword, storeType, limit)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * shoppingItemRepository.findHistorySuggestions(householdId, keyword, storeType, 20) >> suggestions

        and:
        result == suggestions
    }

    def "listHistorySuggestionsはlimitが1〜100ならその値で検索する"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String keyword = "egg"
        String storeType = "DRUGSTORE"
        int limit = 30

        def suggestions = [Mock(ShoppingItemHistorySuggestionModel)]

        when:
        def result = service.listHistorySuggestions(householdId, userId, keyword, storeType, limit)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId)
        1 * shoppingItemRepository.findHistorySuggestions(householdId, keyword, storeType, limit) >> suggestions

        and:
        result == suggestions
    }
}
