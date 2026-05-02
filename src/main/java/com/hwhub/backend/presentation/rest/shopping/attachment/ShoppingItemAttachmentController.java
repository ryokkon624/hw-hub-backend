package com.hwhub.backend.presentation.rest.shopping.attachment;

import com.hwhub.backend.application.service.ShoppingItemAttachmentService;
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.CreateAttachmentRequest;
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.CreateAttachmentResponse;
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.CreateUploadUrlRequest;
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.CreateUploadUrlResponse;
import com.hwhub.backend.presentation.rest.shopping.attachment.dto.ShoppingItemAttachmentResponse;
import com.hwhub.backend.security.CurrentUserId;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shopping-items/{itemId}/attachments")
public class ShoppingItemAttachmentController {

  private final ShoppingItemAttachmentService attachmentService;

  @PostMapping("/upload-url")
  public CreateUploadUrlResponse createUploadUrl(
      @PathVariable("itemId") Long itemId,
      @Valid @RequestBody CreateUploadUrlRequest request,
      @CurrentUserId Long userId) {
    var result =
        attachmentService.createUploadUrl(itemId, request.fileName(), request.mimeType(), userId);

    return new CreateUploadUrlResponse(result.uploadUrl(), result.fileKey());
  }

  @PostMapping
  public CreateAttachmentResponse createAttachment(
      @PathVariable("itemId") Long itemId,
      @Valid @RequestBody CreateAttachmentRequest request,
      @CurrentUserId Long userId) {
    var attachment =
        attachmentService.createAttachment(
            itemId, request.fileKey(), request.fileName(), request.mimeType(), userId);

    return new CreateAttachmentResponse(attachment.getId());
  }

  @GetMapping
  public List<ShoppingItemAttachmentResponse> listAttachments(
      @PathVariable("itemId") Long itemId, @CurrentUserId Long userId) {
    var list = attachmentService.listAttachments(itemId, userId);
    return list.stream()
        .map(
            a ->
                new ShoppingItemAttachmentResponse(
                    a.getId(), a.getFileName(), a.getImageUrl(), a.getSortOrder()))
        .toList();
  }

  @DeleteMapping("/{attachmentId}")
  public void deleteAttachment(
      @PathVariable("itemId") Long itemId,
      @PathVariable("attachmentId") Long attachmentId,
      @CurrentUserId Long userId) {
    attachmentService.deleteAttachment(itemId, attachmentId, userId);
  }
}
