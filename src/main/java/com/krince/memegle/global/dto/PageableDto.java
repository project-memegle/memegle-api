package com.krince.memegle.global.dto;

import com.krince.memegle.global.constant.Criteria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PageableDto {

    @Schema(title = "페이지 번호", description = "몇 페이지의 데이터를 가져올지 명시함", example = "1")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private int page;

    @Schema(title = "페이지당 데이터 개수", description = "하나의 페이지에 몇개의 데이터를 포함시킬지 명시함", example = "10")
    @Min(value = 1, message = "페이지당 데이터 개수는 1 이상이어야 합니다.")
    private int size;

    @Schema(title = "정렬 기준", description = "무엇을 기준으로 데이터를 정렬할지 명시함", example = "CREATED_AT")
    private Criteria criteria;
}
