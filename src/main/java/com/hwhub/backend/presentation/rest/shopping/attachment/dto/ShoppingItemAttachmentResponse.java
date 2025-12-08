package com.hwhub.backend.presentation.rest.shopping.attachment.dto;

public record ShoppingItemAttachmentResponse(
    Long id, String fileName, String imageUrl, Integer sortOrder) {}
