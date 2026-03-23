package com.hwhub.backend.infrastructure.mybatis.custom.entity;

import java.time.LocalDateTime;
import lombok.Data;

/** t_inquiry と m_user、t_inquiry_message を JOIN した管理者向け集計結果 Entity */
@Data
public class AdminInquiryEntity {
  private Long inquiryId;
  private Long userId;
  private String userEmail;
  private String userDisplayName;
  private String category;
  private String status;
  private String title;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private int totalMessageCount;
  private int userMessageCount;
  private int aiMessageCount;
  private int staffMessageCount;
}
