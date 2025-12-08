package com.hwhub.backend.presentation.rest.shopping;

import com.hwhub.backend.application.service.ShoppingItemService;
import com.hwhub.backend.domain.model.ShoppingItemModel;
import com.hwhub.backend.presentation.rest.shopping.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 買い物アイテム（Shopping Item）の参照、作成、更新、ステータス管理を行うAPIコントローラです。
 *
 * <p>このコントローラ内の全てのエンドポイントは、認証（Authentication）と、 アイテムまたは世帯（Household）に対する認可（Authorization）が必要です。
 * ベースパス: /api/
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class ShoppingItemController {

  private final ShoppingItemService shoppingItemService;

  /**
   * 指定された世帯IDに紐づく買い物アイテムを取得します。<br>
   * GET /api/households/{householdId}/shopping-items
   *
   * @param householdId 世帯ID。パス変数として指定します。
   * @param authentication Spring Securityによる認証情報（ユーザーID取得用）
   * @return 指定された世帯IDの買い物アイテムリスト
   * @throws com.hwhub.backend.domain.exception.ResourceNotFoundException
   *     指定された世帯IDが存在しない、またはユーザーが所属していない場合（404/403）
   */
  @GetMapping("/households/{householdId}/shopping-items")
  public ShoppingItemListResponse getShoppingItems(
      @PathVariable("householdId") Long householdId, Authentication authentication) {
    Long userId = Long.parseLong(authentication.getName());

    List<ShoppingItemModel> models = shoppingItemService.getShoppingItems(householdId, userId);

    return ShoppingItemListResponse.fromModelList(models);
  }

  /**
   * 買い物アイテムのお気に入り状態（お気に入り/お気に入り解除）を更新します。<br>
   * PATCH /api/shopping-items/{shoppingItemId}/favorite
   *
   * @param shoppingItemId 買い物アイテムのID
   * @param request お気に入り状態（True/False）を含むリクエストボディ
   * @param authentication 認証ユーザー情報
   * @return HTTPステータス 204 No Content（更新成功）
   * @throws com.hwhub.backend.domain.exception.ResourceNotFoundException アイテムが存在しない場合（404）
   * @throws org.springframework.web.server.ResponseStatusException ユーザーに更新権限がない場合（403 Forbidden）
   */
  @PatchMapping("/shopping-items/{shoppingItemId}/favorite")
  public ResponseEntity<Void> updateFavorite(
      @PathVariable Long shoppingItemId,
      @RequestBody @Valid UpdateFavoriteRequest request,
      Authentication authentication) {
    long userId = Long.parseLong(authentication.getName());

    shoppingItemService.updateFavorite(shoppingItemId, request.favorite(), userId);

    return ResponseEntity.noContent().build(); // 204
  }

  /**
   * 買い物アイテムのステータス（購入済み、未購入など）を更新します。<br>
   * PATCH /api/shopping-items/{shoppingItemId}/status
   *
   * @param shoppingItemId 買い物アイテムのID
   * @param request 更新後のステータスコードを含むリクエストボディ
   * @param authentication 認証ユーザー情報
   * @return HTTPステータス 204 No Content（更新成功）
   * @throws com.hwhub.backend.domain.exception.ResourceNotFoundException アイテムが存在しない場合（404）
   */
  @PatchMapping("/shopping-items/{shoppingItemId}/status")
  public ResponseEntity<Void> updateStatus(
      @PathVariable Long shoppingItemId,
      @RequestBody @Valid UpdateStatusRequest request,
      Authentication authentication) {
    long userId = Long.parseLong(authentication.getName());

    shoppingItemService.updateStatus(shoppingItemId, request.status(), userId);

    return ResponseEntity.noContent().build();
  }

  /**
   * 指定された世帯に新しい買い物アイテムを作成します。<br>
   * POST /api/households/{householdId}/shopping-items
   *
   * @param householdId アイテムを作成する世帯ID
   * @param request 作成するアイテムの情報（名前、数量など）を含むリクエストボディ
   * @param authentication 認証ユーザー情報
   * @return HTTPステータス 201 Created と共に、作成されたアイテムのDTO
   */
  @PostMapping("/households/{householdId}/shopping-items")
  public ResponseEntity<ShoppingItemDto> create(
      @PathVariable Long householdId,
      @RequestBody @Valid CreateShoppingItemRequest request,
      Authentication authentication) {

    long userId = Long.parseLong(authentication.getName());

    ShoppingItemModel inserted =
        shoppingItemService.create(
            ShoppingItemModel.create(
                householdId, request.getName(), request.getMemo(), request.getStoreType()),
            request.getSourceShoppingItemId(),
            userId);

    ShoppingItemDto dto = ShoppingItemDto.fromModel(inserted);
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
  }

  /**
   * 既存の買い物アイテムの情報を全体的に更新します（PUT）。<br>
   * PUT /api/households/{householdId}/shopping-items/{shoppingItemId}
   *
   * @param householdId 対象アイテムが所属する世帯ID
   * @param shoppingItemId 更新する買い物アイテムのID
   * @param request 更新内容を含むリクエストボディ
   * @param authentication 認証ユーザー情報
   * @return HTTPステータス 200 OK と共に、更新後のアイテムのDTO
   */
  @PutMapping("/households/{householdId}/shopping-items/{shoppingItemId}")
  public ResponseEntity<ShoppingItemDto> update(
      @PathVariable Long householdId,
      @PathVariable Long shoppingItemId,
      @Valid @RequestBody UpdateShoppingItemRequest request,
      Authentication authentication) {

    long userId = Long.parseLong(authentication.getName());

    ShoppingItemModel updated =
        shoppingItemService.update(
            householdId,
            shoppingItemId,
            request.name(),
            request.memo(),
            request.storeType(),
            userId);

    ShoppingItemDto dto = ShoppingItemDto.fromModel(updated);
    return ResponseEntity.ok(dto);
  }
}
