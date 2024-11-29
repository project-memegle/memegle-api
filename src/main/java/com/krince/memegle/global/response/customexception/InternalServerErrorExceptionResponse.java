package com.krince.memegle.global.response.customexception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;

public class InternalServerErrorExceptionResponse extends ExceptionResponse {

    @Schema(title = "응답 성공 유무", description = "응답 성공 유무", example = "false")
    private boolean success;

    @Schema(title = "상태", description = "상태", example = "Internal Server Error")
    private String status;

    @Schema(title = "상태코드", description = "상태코드", example = "50000")
    private int code;

    @Schema(title = "예외 메시지", description = "예외 메시지", example = "서버 에러입니다 개발자에게 문의해주세요.")
    private String message;

    public InternalServerErrorExceptionResponse(ResponseCode responseCode) {
        super(responseCode);
    }

    public InternalServerErrorExceptionResponse(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
}
