package com.krince.memegle.client.domain.post.controller;

import com.krince.memegle.client.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.client.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.global.response.ExceptionResponseDto;
import com.krince.memegle.global.response.GlobalResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "게시물", description = "게시물 관련 API")
public interface PostController {

    @GetMapping()
    @Operation(summary = "모든 게시물 조회", description = "게시물 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "500", description = "예상치 못한 오류", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    ResponseEntity<GlobalResponseDto<List<ResponsePostListDto>>> getPosts();

    @PostMapping()
    @Operation(summary = "밈 이미지 등록 신청", description = "밈 이미지를 등록 신청합니다. 관리자의 승인이 있으면 게시물로 등록됩니다.")
    @ApiResponse(responseCode = "204", description = "등록 성공")
    @ApiResponse(responseCode = "400", description = "누락, 잘못된 요청 양식")
    @ApiResponse(responseCode = "500", description = "예상치 못한 오류")
    ResponseEntity<Void> resistPost(@RequestPart("mimeImage") @Valid MultipartFile mimeImage,
                                    @RequestPart("dto") @Valid RequestResistPostDto requestResistPostDto) throws IOException;
}
