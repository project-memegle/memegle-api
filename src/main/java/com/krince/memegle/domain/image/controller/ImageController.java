package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.global.ImageCategory;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.response.customexception.InternalServerErrorExceptionResponse;
import com.krince.memegle.global.response.customexception.InvalidValueExceptionResponse;
import com.krince.memegle.global.response.customexception.NotFoundResourceExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;

@Tag(name = "이미지", description = "이미지 관련 API")
public interface ImageController {

    @Operation(summary = "밈 이미지 조회", description = "밈 이미지를 조회합니다.")
    @ApiResponse(description = "밈 이미지 조회 성공", responseCode = "200")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "400",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "없는 밈 이미지 id", responseCode = "404",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "500",
    content = @Content(mediaType = APPLICATION_JSON_VALUE,
    schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<SuccessResponse<ViewImageDto>> getImage(
            Long imageId,
            Authentication authentication,
            @Parameter(hidden = true) CustomUserDetails userDetails);

    @Operation(summary = "카테고리 이미지 리스트 조회", description = "선택한 카테고리의 이미지 리스트를 조회합니다.")
    @ApiResponse(description = "카테고리 이미지 리스트 조회 성공", responseCode = "200")
    ResponseEntity<SuccessResponse<List<ViewImageDto>>> getCategoryImages(
            @RequestParam ImageCategory imageCategory,
            @ModelAttribute @Valid PageableDto pageableDto,
            @Parameter(hidden = true) CustomUserDetails userDetails);

    @Operation(summary = "밈 이미지 등록 요청", description = "밈 이미지를 등록 요청합니다.")
    @ApiResponse(description = "밈 이미지 등록 요청 성공", responseCode = "204")
    ResponseEntity<ResponseCode> registMemeImage(
            @RequestParam ImageCategory imageCategory,
            @RequestPart MultipartFile memeImage,
            @RequestPart @NotBlank String tags,
            @RequestPart @NotNull String delimiterFile,
            @Parameter(hidden = true) CustomUserDetails userDetails
    ) throws IOException;
}
