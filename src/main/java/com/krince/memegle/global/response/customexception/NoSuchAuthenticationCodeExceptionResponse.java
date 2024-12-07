package com.krince.memegle.global.response.customexception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ExceptionResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

public class NoSuchAuthenticationCodeExceptionResponse extends ExceptionResponse {

    @Schema(title = "응답 성공 유무", description = "응답 성공 유무", example = "false")
    private boolean success;

    @Schema(title = "상태", description = "상태", example = "No Such Authentication Code")
    private String status;

    @Schema(title = "상태코드", description = "상태코드", example = "40106")
    private int code;

    @Schema(title = "예외 메시지", description = "예외 메시지", example = "인증 이메일 정보가 없거나 만료기간이 초과되었습니다.")
    private String message;

    public NoSuchAuthenticationCodeExceptionResponse(ExceptionResponseCode responseCode) {
        super(responseCode);
    }

    public NoSuchAuthenticationCodeExceptionResponse(ExceptionResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
