package com.hwhub.backend.presentation.rest.shopping.attachment;

import com.hwhub.backend.application.service.ShoppingItemAttachmentService;
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shopping-items/{itemId}/attachments")
public class ShoppingItemAttachmentController {

  private final ShoppingItemAttachmentService attachmentService;

  @PostMapping("/upload-url")
  public CreateUploadUrlResponse createUploadUrl(
      @PathVariable Long itemId,
      @Valid @RequestBody CreateUploadUrlRequest request,
      Authentication authentication) {

    long userId = Long.parseLong(authentication.getName());

    var result =
        attachmentService.createUploadUrl(itemId, request.fileName(), request.mimeType(), userId);

    return new CreateUploadUrlResponse(result.uploadUrl(), result.fileKey());
  }

  @PostMapping
  public CreateAttachmentResponse createAttachment(
      @PathVariable Long itemId,
      @Valid @RequestBody CreateAttachmentRequest request,
      Authentication authentication) {

    long userId = Long.parseLong(authentication.getName());

    var attachment =
        attachmentService.createAttachment(
            itemId, request.fileKey(), request.fileName(), request.mimeType(), userId);

    return new CreateAttachmentResponse(attachment.getId());
  }

  @GetMapping
  public List<ShoppingItemAttachmentResponse> listAttachments(
      @PathVariable Long itemId, Authentication authentication) {

    long userId = Long.parseLong(authentication.getName());

    var list = attachmentService.listAttachments(itemId, userId);
    return list.stream()
        .map(
            a ->
                new ShoppingItemAttachmentResponse(
                    a.getShoppingItemId(), a.getFileName(), a.getImageUrl(), a.getSortOrder()))
        .toList();
  }

  @DeleteMapping("/{attachmentId}")
  public void deleteAttachment(
      @PathVariable Long itemId, @PathVariable Long attachmentId, Authentication authentication) {

    long userId = Long.parseLong(authentication.getName());

    attachmentService.deleteAttachment(itemId, attachmentId, userId);
  }
}
