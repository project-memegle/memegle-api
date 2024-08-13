package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(name = "이미지", description = "이미지 관련 API")
public interface ImageController {

    @Operation(summary = "밈 이미지 조회", description = "밈 이미지를 조회합니다.")
    @ApiResponse(description = "밈 이미지 조회 성공", responseCode = "200")
    ResponseEntity<SuccessResponse<ViewImageDto>> getImage(Long imageId, Authentication authentication, CustomUserDetails userDetails);

}
