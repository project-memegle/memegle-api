package com.krince.memegle.admin.domain.image.controller;

import com.krince.memegle.admin.domain.image.dto.request.RequestConfirmMemeImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "관리자", description = "관리자 관련 API")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface ImageController {

    @Operation(
            summary = "밈 이미지 승인 및 등록 & 반려 처리",
            description = "해당 밈 이미지를 회원 게시판에 등록 및 반려처리합니다.", security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "등록 및 반려 처리 성공")
    @ApiResponse(responseCode = "400", description = "필수값 누락")
    ResponseEntity<Void> confirmMemeImage(
            @Parameter(description = "밈 이미지 고유번호") @PathVariable Long mimeImageId,
            @RequestBody RequestConfirmMemeImageDto requestConfirmMemeImageDto
    );
}
