package com.hwhub.backend.presentation.rest.shopping.history;

import com.hwhub.backend.application.service.ShoppingItemService;
import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel;
import com.hwhub.backend.presentation.rest.shopping.history.dto.ShoppingItemHistorySuggestionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/households/{householdId}/shopping-items")
public class ShoppingItemHistoryController {

  private final ShoppingItemService shoppingItemService;

  @GetMapping("/history-suggestions")
  public List<ShoppingItemHistorySuggestionResponse> listHistorySuggestions(
      @PathVariable Long householdId,
      @RequestParam(value = "q", required = false) String keyword,
      @RequestParam(value = "storeType", required = false) String storeType,
      @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
      Authentication authentication) {
    long userId = Long.parseLong(authentication.getName());

    List<ShoppingItemHistorySuggestionModel> list =
        shoppingItemService.listHistorySuggestions(householdId, userId, keyword, storeType, limit);

    return list.stream()
        .map(
            s ->
                new ShoppingItemHistorySuggestionResponse(
                    s.getName(),
                    s.getMemo(),
                    s.getStoreType(),
                    s.getLastPurchasedDate(),
                    s.getPurchaseCount(),
                    s.getSourceShoppingItemId(),
                    s.getFavorite()))
        .toList();
  }
}
