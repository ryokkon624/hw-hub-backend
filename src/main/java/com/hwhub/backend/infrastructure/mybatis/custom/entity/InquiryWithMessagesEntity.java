package com.hwhub.backend.infrastructure.mybatis.custom.entity;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryMessage;
import java.util.Date;
import java.util.List;
import lombok.Data;

/** t_inquiry と t_inquiry_message を LEFT JOIN した結果を受け取る専用Entity */
@Data
public class InquiryWithMessagesEntity {
  private Long inquiryId;
  private Long userId;
  private String category;
  private String status;
  private String title;
  private Date createdAt;
  private List<TInquiryMessage> messages;
}
