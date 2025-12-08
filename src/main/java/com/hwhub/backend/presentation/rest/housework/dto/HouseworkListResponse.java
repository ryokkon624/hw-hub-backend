package com.hwhub.backend.presentation.rest.housework.dto;

import java.util.List;

/** 家事一覧APIのレスポンス。 将来 totalCount やページング情報を追加しやすいようにラッパにしている。 */
public record HouseworkListResponse(List<HouseworkDto> houseworks) {}
