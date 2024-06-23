package com.krince.memegle.admin.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Schema(title = "ResponseGetAdminPostsDto")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGetAdminPostsDto {

    @NotBlank
    @Schema(description = "밈 이미지 게시물 고유번호")
    private Long postId;

    @NotBlank
    @Schema(description = "밈 이미지 고유번호")
    private Long imageId;

    @NotBlank
    @Schema(description = "밈 이미지 url")
    private String memeImageUrl;

    @NotBlank
    @Schema(description = "밈 이미지 설명")
    private String content;

    @NotBlank
    @Schema(description = "등록 일시")
    private Date createdAt;
    }