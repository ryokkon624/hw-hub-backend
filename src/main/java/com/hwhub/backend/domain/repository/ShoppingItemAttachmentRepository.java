package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.ShoppingItemAttachment;
import java.util.List;
import java.util.Optional;

public interface ShoppingItemAttachmentRepository {

  ShoppingItemAttachment save(ShoppingItemAttachment attachment, long userId, String program);

  List<ShoppingItemAttachment> findByShoppingItemId(Long shoppingItemId);

  Optional<ShoppingItemAttachment> findById(Long id);

  void deleteById(Long id);
}
