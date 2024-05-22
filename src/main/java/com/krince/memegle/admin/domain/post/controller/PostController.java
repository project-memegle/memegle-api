package com.krince.memegle.admin.domain.post.controller;

import com.krince.memegle.admin.domain.post.dto.response.ResponseGetAdminPostsDto;
import com.krince.memegle.global.response.GlobalResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "관리자", description = "관리자 관련 API")
public interface PostController {
    @Operation(summary = "관리자 페이지 홈 화면 조회", description = "관리자 페이지 홈 화면(게시글 목록) 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<GlobalResponseDto<List<ResponseGetAdminPostsDto>>> getAdminPosts();
}
