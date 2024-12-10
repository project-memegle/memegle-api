package com.krince.memegle.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "성공 응답 공통 Response")
public class SuccessResponse<T> extends GlobalResponse {

    @Schema(title = "결과 데이터", description = "결과 데이터")
    private final T results;

    public SuccessResponse(SuccessResponseCode responseCode, T results) {
        super(responseCode);
        this.results = results;
    }
}
