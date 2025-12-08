package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.presentation.rest.shopping.attachment.dto.ShoppingItemAttachmentResponse;

public record ListShoppingItemAttachmentsResponse(
    java.util.List<ShoppingItemAttachmentResponse> attachments) {}
