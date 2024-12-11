package com.krince.memegle.domain.category.controller;

import com.krince.memegle.domain.category.dto.CategoryDto;
import com.krince.memegle.global.constant.Criteria;
import com.krince.memegle.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "카테고리", description = "카테고리 관련 API")
public abstract class BaseCategoryController {

    @GetMapping
    @Operation(summary = "카테고리 조회(미구현 api)", description = "카테고리 리스트를 조회합니다. queryString으로 정렬 기준을 정합니다.")
    @ApiResponse(description = "카테고리 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "필수값 누락", responseCode = "40003", ref = "#/components/responses/40003")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<List<CategoryDto>>> getCategories(@Parameter(description = "POPULARITY만 사용하세요.") @RequestParam Criteria criteria);
}
