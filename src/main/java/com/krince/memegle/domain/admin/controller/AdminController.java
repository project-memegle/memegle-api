package com.krince.memegle.domain.admin.controller;

import com.krince.memegle.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.domain.admin.dto.response.ResponseGetAdminPostsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "관리자", description = "관리자 관련 API")
public interface AdminController {

    @PostMapping("/sign/in")
    @Operation(summary = "관리자 로그인", description = "관리자 계정에 로그인합니다.")
    @ApiResponse(description = "로그인 성공", responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class)))
    ResponseEntity<Void> login(@RequestBody RequestAdminLoginDto requestAdminLoginDto);

    @GetMapping("/posts")
    @Operation(summary = "관리자 페이지 홈 화면 조회", description = "관리자 페이지 홈 화면(게시글 목록) 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseGetAdminPostsDto.class))))
    })
    ResponseEntity<List<ResponseGetAdminPostsDto>> getAdminPosts();
}
