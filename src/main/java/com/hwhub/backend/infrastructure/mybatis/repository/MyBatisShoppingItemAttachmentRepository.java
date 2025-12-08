package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.ShoppingItemAttachment;
import com.hwhub.backend.domain.repository.ShoppingItemAttachmentRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.ShoppingItemAttachmentConverter;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemAttachment;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemAttachmentExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TShoppingItemAttachmentMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisShoppingItemAttachmentRepository implements ShoppingItemAttachmentRepository {

  private final TShoppingItemAttachmentMapper mapper;

  @Override
  public ShoppingItemAttachment save(ShoppingItemAttachment model, long userId, String program) {
    TShoppingItemAttachment entity = ShoppingItemAttachmentConverter.toEntity(model);
    if (entity.getShoppingItemAttachmentId() == null) {
      entity.setCreateUserId(userId);
      entity.setCreateProgram(program);
      entity.setUpdateUserId(userId);
      entity.setUpdateProgram(program);
      mapper.insertSelective(entity);
    } else {
      entity.setUpdateUserId(userId);
      entity.setUpdateProgram(program);
      mapper.updateByPrimaryKeySelective(entity);
    }
    return ShoppingItemAttachmentConverter.toModel(entity);
  }

  @Override
  public Optional<ShoppingItemAttachment> findById(Long id) {
    var entity = mapper.selectByPrimaryKey(id);
    return Optional.ofNullable(entity).map(ShoppingItemAttachmentConverter::toModel);
  }

  @Override
  public List<ShoppingItemAttachment> findByShoppingItemId(Long shoppingItemId) {
    TShoppingItemAttachmentExample example = new TShoppingItemAttachmentExample();
    example.createCriteria().andShoppingItemIdEqualTo(shoppingItemId);

    return mapper.selectByExample(example).stream()
        .map(ShoppingItemAttachmentConverter::toModel)
        .toList();
  }

  @Override
  public void deleteById(Long id) {
    mapper.deleteByPrimaryKey(id);
  }
}
