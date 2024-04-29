package com.krince.memegle.domain.post.controller;

import com.krince.memegle.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.domain.post.dto.response.ResponsePostListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "게시물", description = "게시물 관련 API")
public interface PostController {

    @GetMapping()
    @Operation(summary = "모든 게시물 조회", description = "게시물 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponsePostListDto.class)))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = String.class)))
    })
    ResponseEntity<List<ResponsePostListDto>> getPosts();

    @PostMapping()
    @Operation(summary = "밈 이미지 등록 신청", description = "밈 이미지를 등록 신청합니다. 관리자의 승인이 있으면 게시물로 등록됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "누락, 잘못된 요청 양식", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = String.class)))
    })
    ResponseEntity<Void> resistPost(@ModelAttribute RequestResistPostDto requestResistPostDto);
}
