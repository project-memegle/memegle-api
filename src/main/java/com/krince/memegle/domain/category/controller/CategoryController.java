package com.krince.memegle.domain.category.controller;

import com.krince.memegle.domain.category.dto.CategoryDto;
import com.krince.memegle.global.Criteria;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.response.customexception.InternalServerErrorExceptionResponse;
import com.krince.memegle.global.response.customexception.InvalidValueExceptionResponse;
import com.krince.memegle.global.response.customexception.RequireValueExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.http.MediaType.*;

@Tag(name = "카테고리", description = "카테고리 관련 API")
public interface CategoryController {

    @Operation(summary = "카테고리 조회", description = "카테고리 리스트를 조회합니다. queryString으로 정렬 기준을 정합니다.")
    @ApiResponse(description = "카테고리 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<SuccessResponse<List<CategoryDto>>> getCategories(@Parameter(description = "POPULARITY만 사용하세요.") @RequestParam Criteria criteria);

}
