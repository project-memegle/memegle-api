package com.krince.memegle.domain.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(title = "관리자 게시글 리스트 조회 api response")
public class ResponseGetAdminPostsDto {

    @NotBlank
    @Schema(description = "밈 이미지 고유번호", example = "1")
    private Long mimeImageId;

    @NotBlank
    @Schema(description = "밈 이미지 url", example = "http://172.0.0.1:9802/uploads/dev/171463841004649253432.jpeg")
    private String mimeImageUrl;

    @NotBlank
    @Schema(description = "등록 일시", example = "2024-05-03T14:00:00")
    private LocalDateTime createdAt;
}
