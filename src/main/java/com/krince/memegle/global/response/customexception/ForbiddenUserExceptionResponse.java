package com.krince.memegle.global.response.customexception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ExceptionResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

public class ForbiddenUserExceptionResponse extends ExceptionResponse {

    @Schema(title = "응답 성공 유무", description = "응답 성공 유무", example = "false")
    private boolean success;

    @Schema(title = "상태", description = "상태", example = "Forbidden")
    private String status;

    @Schema(title = "상태코드", description = "상태코드", example = "40300")
    private int code;

    @Schema(title = "예외 메시지", description = "예외 메시지", example = "해당 리소스에 접근 권한이 없습니다.")
    private String message;

    public ForbiddenUserExceptionResponse(ExceptionResponseCode responseCode) {
        super(responseCode);
    }

    public ForbiddenUserExceptionResponse(ExceptionResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
