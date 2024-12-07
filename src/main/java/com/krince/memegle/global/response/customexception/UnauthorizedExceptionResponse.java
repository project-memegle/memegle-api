package com.krince.memegle.global.response.customexception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ExceptionResponseCode;
import com.krince.memegle.global.response.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

public class UnauthorizedExceptionResponse extends ExceptionResponse {

    @Schema(title = "응답 성공 유무", description = "응답 성공 유무", example = "false")
    private boolean success;

    @Schema(title = "상태", description = "상태", example = "Unauthorized")
    private String status;

    @Schema(title = "상태코드", description = "상태코드", example = "40100")
    private int code;

    @Schema(title = "예외 메시지", description = "예외 메시지", example = "인증 정보가 일치하지 않습니다.")
    private String message;

    public UnauthorizedExceptionResponse(ExceptionResponseCode responseCode) {
        super(responseCode);
    }

    public UnauthorizedExceptionResponse(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
