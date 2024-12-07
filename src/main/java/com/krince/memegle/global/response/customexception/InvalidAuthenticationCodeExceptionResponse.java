package com.krince.memegle.global.response.customexception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ExceptionResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

public class InvalidAuthenticationCodeExceptionResponse extends ExceptionResponse {

    @Schema(title = "응답 성공 유무", description = "응답 성공 유무", example = "false")
    private boolean success;

    @Schema(title = "상태", description = "상태", example = "Invalid Authentication Code")
    private String status;

    @Schema(title = "상태코드", description = "상태코드", example = "40105")
    private int code;

    @Schema(title = "예외 메시지", description = "예외 메시지", example = "이메일 인증코드가 일치하지 않습니다.")
    private String message;

    public InvalidAuthenticationCodeExceptionResponse(ExceptionResponseCode responseCode) {
        super(responseCode);
    }

    public InvalidAuthenticationCodeExceptionResponse(ExceptionResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
