package com.krince.memegle.global.response.customexception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

public class DuplicateUserExceptionResponse extends ExceptionResponse {

    @Schema(title = "응답 성공 유무", description = "응답 성공 유무", example = "false")
    private boolean success;

    @Schema(title = "상태", description = "상태", example = "Duplicate User")
    private String status;

    @Schema(title = "상태코드", description = "상태코드", example = "40002")
    private int code;

    @Schema(title = "예외 메시지", description = "예외 메시지", example = "이미 존재하는 회원입니다.")
    private String message;

    public DuplicateUserExceptionResponse(ResponseCode responseCode) {
        super(responseCode);
    }

    public DuplicateUserExceptionResponse(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
