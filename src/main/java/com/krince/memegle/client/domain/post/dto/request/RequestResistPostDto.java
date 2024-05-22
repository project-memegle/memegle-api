package com.krince.memegle.client.domain.post.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "RequestResistPostDto")
public class RequestResistPostDto {

    @NotBlank(message = "content는 필수 입력값입니다.")
    @Schema(description = "밈 이미지 설명 내용")
    private String content;
}
