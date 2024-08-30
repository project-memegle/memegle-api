package com.krince.memegle.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GlobalResponse {

    @Schema(title = "요청 성공 여부", description = "요청 성공 여부", example = "true")
    private final Boolean success;

    @Schema(title = "HTTP 상태", description = "HTTP 상태")
    private final String status;

    @Schema(title = "HTTP 코드 번호", description = "HTTP 코드 번호")
    private final Integer code;

    @Schema(title = "메시지", description = "메시지")
    private final String message;

    public GlobalResponse(ResponseCode responseCode) {
        this.success = responseCode.getIsSuccess();
        this.status = responseCode.getHttpStatus();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public GlobalResponse(ResponseCode responseCode, String message) {
        this.success = responseCode.getIsSuccess();
        this.status = responseCode.getHttpStatus();
        this.code = responseCode.getCode();
        this.message = message;
    }
}
