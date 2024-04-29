package com.krince.memegle.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Schema(title = "밈 이미지 등록 api request body")
public class RequestResistPostDto {

    @NotBlank(message = "이미지를 등록해주세요")
    @Schema(description = "밈 이미지 파일")
    private MultipartFile mimeImage;

    @NotNull(message = "content는 null일 수 없습니다.")
    @Size(max = 2000)
    @Schema(description = "밈 이미지 소개 본문", example = "this is infinite challenge mime!!")
    private String content;
}
