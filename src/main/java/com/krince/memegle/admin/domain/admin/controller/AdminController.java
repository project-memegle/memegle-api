package com.krince.memegle.admin.domain.admin.controller;

import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.admin.domain.admin.dto.response.RequestConfirmMimeImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "관리자", description = "관리자 관련 API")
public interface AdminController {

    @Operation(summary = "관리자 로그인", description = "관리자 계정에 로그인합니다.")
    @ApiResponse(description = "로그인 성공", responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class)))
    ResponseEntity<Void> login(@Valid @RequestBody RequestAdminLoginDto requestAdminLoginDto);

    @Operation(summary = "밈 이미지 승인 및 등록 & 반려 처리", description = "해당 밈 이미지를 회원 게시판에 등록 및 반려처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "등록 및 반려 처리 성공", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    ResponseEntity<Void> confirmMimeImage(
            @Parameter(description = "밈 이미지 고유번호", example = "1") @PathVariable Long mimeImageId,
            @RequestBody RequestConfirmMimeImageDto requestConfirmMimeImageDto
    );
}
