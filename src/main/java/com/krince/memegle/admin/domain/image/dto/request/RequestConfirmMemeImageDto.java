package com.krince.memegle.admin.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(title = "RequestConfirmMimeImageDto")
public class RequestConfirmMemeImageDto {

    @NotBlank(message = "isConfirm은 필수 입력값입니다.")
    @Schema(description = "승인 여부")
    private Boolean isConfirm;
}
