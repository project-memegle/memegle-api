package com.krince.memegle.domain.image.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ImageIdDto {

    @NotBlank(message = "비어있거나 null 일 수 없습니다.")
    @Min(value = 1, message = "1보다 큰 숫자여야합니다.")
    private Long imageId;
}
