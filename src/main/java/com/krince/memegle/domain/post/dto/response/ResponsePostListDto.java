package com.krince.memegle.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@Schema(title = "메인 페이지 게시물 리스트 조회 api response")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePostListDto {

    @NotBlank
    @Schema(description = "게시물 id", example = "1")
    private Long postId;

    @NotBlank
    @Schema(description = "게시물 이미지 url", example = "http://localhost:8080/uploads/dev/314798134245.jpg")
    private String postImageUrl;

    @NotBlank
    @Builder.Default
    @Schema(description = "좋아요 개수", example = "0")
    private Long likeCount = 0L;

    @NotBlank
    @Schema(description = "게시물 게시일", example = "2024-04-29T14:30:00Z")
    private Date createdAt;
}
