package com.krince.memegle.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseCode implements ResponseCode {

    OK(true, 20000, 200, "OK", "요청이 성공적으로 처리되었습니다. 요청한 데이터를 반환합니다."),
    CREATED(true, 20100, 201, "Created", "요청이 성공적으로 처리되었습니다. 새로운 리소스가 생성되었습니다."),
    NO_CONTENT(true, 20400, 204, "No Content", "요청이 성공적으로 처리되었습니다. 응답 데이터는 없습니다."),
    ;

    private final Boolean isSuccess;
    private final Integer code;
    private final Integer httpCode;
    private final String httpStatus;
    private final String message;
}
