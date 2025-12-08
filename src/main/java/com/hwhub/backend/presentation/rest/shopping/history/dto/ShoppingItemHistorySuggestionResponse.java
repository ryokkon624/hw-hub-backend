package com.hwhub.backend.presentation.rest.shopping.history.dto;

import java.time.LocalDate;

/** 買い物履歴サジェスト API のレスポンス DTO */
public record ShoppingItemHistorySuggestionResponse(
    String name,
    String memo,
    String storeType,
    LocalDate lastPurchasedDate,
    long purchaseCount,
    Long sourceShoppingItemId,
    String favorite) {}
