package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.FavoriteFlag;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.ShoppingItemStatus;
import com.hwhub.backend.domain.model.ShoppingItemAttachment;
import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel;
import com.hwhub.backend.domain.model.ShoppingItemModel;
import com.hwhub.backend.domain.repository.ShoppingItemAttachmentRepository;
import com.hwhub.backend.domain.repository.ShoppingItemRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingItemService {

  private final ShoppingItemRepository shoppingItemRepository;
  private final HouseholdAuthorizationService householdAuthorizationService;
  private final ShoppingItemAttachmentRepository shoppingItemAttachmentRepository;

  /** 世帯の買い物アイテム一覧を取得する */
  @Transactional(readOnly = true)
  public List<ShoppingItemModel> getShoppingItems(long householdId, long userId) {

    if (!householdAuthorizationService.canAccessHousehold(householdId, userId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
    }

    return shoppingItemRepository.findByHouseholdId(householdId);
  }

  /** 世帯のお気に入り買い物アイテム一覧を取得する */
  @Transactional(readOnly = true)
  public List<ShoppingItemModel> getFavoriteShoppingItems(long householdId, long userId) {

    if (!householdAuthorizationService.canAccessHousehold(householdId, userId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
    }

    return shoppingItemRepository.findFavoritesByHouseholdId(householdId);
  }

  public void updateFavorite(long shoppingItemId, String favorite, long userId) {

    var opt = shoppingItemRepository.findById(shoppingItemId);
    if (opt.isEmpty()) {
      throw new ResourceNotFoundException("SHOPPING_ITEM_NOT_FOUND");
    }
    var item = opt.get();

    // 認可チェック
    if (!householdAuthorizationService.canAccessHousehold(item.getHouseholdId(), userId)) {
      throw new AccessDeniedException("You do not have access to this household.");
    }

    if (FavoriteFlag.FAVORITE.getCode().equals(favorite)) {
      item.favorite();
    } else {
      item.clearFavorite();
    }

    shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.getCode());
  }

  public void updateStatus(long shoppingItemId, String status, long userId) {

    var itemOpt = shoppingItemRepository.findById(shoppingItemId);
    if (itemOpt.isEmpty()) {
      throw new ResourceNotFoundException("SHOPPING_ITEM_NOT_FOUND");
    }
    var item = itemOpt.get();

    if (!householdAuthorizationService.canAccessHousehold(item.getHouseholdId(), userId)) {
      throw new AccessDeniedException("You do not have access to this household.");
    }

    ShoppingItemStatus shoppingItemStatus = ShoppingItemStatus.fromCode(status);
    switch (shoppingItemStatus) {
      case NOT_PURCHASED -> item.notPurcased();
      case PURCHASED -> item.purchased();
      default -> item.inBasket();
    }

    shoppingItemRepository.update(item, userId, ProgramType.ONL_SHP.getCode());
  }

  @Transactional
  public ShoppingItemModel create(ShoppingItemModel model, Long sourceShoppingItemId, long userId) {

    // 認可チェック
    if (!householdAuthorizationService.canAccessHousehold(model.getHouseholdId(), userId)) {
      throw new AccessDeniedException("You do not have access to this household.");
    }

    ShoppingItemModel saved =
        shoppingItemRepository.insert(model, userId, ProgramType.ONL_SHP.getCode());

    if (sourceShoppingItemId == null) {
      return saved;
    }

    var sourceItemOpt = shoppingItemRepository.findById(sourceShoppingItemId);
    if (sourceItemOpt.isEmpty()) {
      // 見つからなくても、本体は作れているのでそのまま返す
      return saved;
    }
    var sourceItem = sourceItemOpt.get();

    if (!sourceItem.getHouseholdId().equals(saved.getHouseholdId())) {
      // household が異なるitemをコピーしようとしていたら無視
      return saved;
    }

    var sourceAttachments =
        shoppingItemAttachmentRepository.findByShoppingItemId(sourceShoppingItemId);

    int nextSortOrder = 1;
    for (ShoppingItemAttachment src : sourceAttachments) {
      ShoppingItemAttachment atttach =
          ShoppingItemAttachment.create(
              saved.getShoppingItemId(),
              src.getFileKey(),
              src.getFileName(),
              src.getMimeType(),
              nextSortOrder++);
      shoppingItemAttachmentRepository.save(atttach, userId, ProgramType.ONL_SHP.getCode());
    }
    return saved;
  }

  @Transactional
  public ShoppingItemModel update(
      Long householdId,
      Long shoppingItemId,
      String name,
      String memo,
      String storeType,
      Long userId) {

    ShoppingItemModel model =
        shoppingItemRepository
            .findById(shoppingItemId)
            .orElseThrow(() -> new IllegalArgumentException("shopping item not found"));

    // 認可チェック
    if (!model.getHouseholdId().equals(householdId)) {
      throw new IllegalStateException("household mismatch");
    }
    householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId);

    model.update(name, memo, storeType);
    return shoppingItemRepository.update(model, userId, ProgramType.ONL_SHP.getCode());
  }

  @Transactional
  public void delete(long shoppingItemId, long userId) {
    var itemOpt = shoppingItemRepository.findById(shoppingItemId);
    if (itemOpt.isEmpty()) {
      throw new ResourceNotFoundException("SHOPPING_ITEM_NOT_FOUND");
    }
    var item = itemOpt.get();

    if (!householdAuthorizationService.canAccessHousehold(item.getHouseholdId(), userId)) {
      throw new AccessDeniedException("You do not have access to this household.");
    }

    // 添付画像を一括削除
    shoppingItemAttachmentRepository.deleteByShoppingItemId(shoppingItemId);

    shoppingItemRepository.deleteById(shoppingItemId);
  }

  @Transactional(readOnly = true)
  public List<ShoppingItemHistorySuggestionModel> listHistorySuggestions(
      Long householdId, Long userId, String keyword, String storeType, int limit) {
    // household 認可チェック
    householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId);

    int actualLimit = (limit <= 0 || limit > 100) ? 20 : limit;

    return shoppingItemRepository.findHistorySuggestions(
        householdId, keyword, storeType, actualLimit);
  }
}
