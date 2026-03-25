package com.hwhub.backend.domain.model;

import lombok.Data;

@Data
public class AdminUserSearchCondition {
  private String email; // 部分一致（任意）
  private Boolean isActive; // true/false/null=全件
  private String locale; // 完全一致（任意）
}
