package com.krince.memegle.admin.domain.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "밈 이미지 등록 승인 및 반려 처리 requestBody")
public class RequestConfirmMimeImageDto {

    @NotBlank(message = "isConfirm은 필수 입력값입니다.")
    @Schema(description = "승인 여부", example = "true")
    private Boolean isConfirm;
}
