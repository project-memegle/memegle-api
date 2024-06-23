package com.krince.memegle.client.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(title = "ResponsePostListDto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponsePostListDto {

    @NotBlank
    @Schema(description = "게시물 id")
    private Long postId;

    @NotBlank
    @Schema(description = "게시물 이미지 url")
    private String postImageUrl;

    @NotBlank
    @Schema(description = "좋아요 개수")
    private Long likeCount;

    @NotBlank
    @Schema(description = "게시물 게시일")
    private Date createdAt;
}
