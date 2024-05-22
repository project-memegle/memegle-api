package com.krince.memegle.admin.domain.admin.controller;

import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "관리자", description = "관리자 관련 API")
public interface AdminController {

    @Operation(summary = "관리자 로그인", description = "관리자 계정에 로그인합니다.")
    @ApiResponse(description = "로그인 성공", responseCode = "204")
    ResponseEntity<Void> login(@RequestBody @Valid RequestAdminLoginDto requestAdminLoginDto);
}
