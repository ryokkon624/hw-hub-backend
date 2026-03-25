package com.hwhub.backend.domain.model;

public record AdminUserSearchCondition(
    String email, // 部分一致（任意）
    Boolean isActive, // true/false/null=全件
    String locale // 完全一致（任意）
    ) {}
