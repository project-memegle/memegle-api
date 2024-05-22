package com.krince.memegle.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK(20000, "OK", "요청이 성공적으로 처리되었습니다. 요청한 데이터를 반환합니다."),
    CREATED(20100, "CREATED", "요청이 성공적으로 처리되었습니다. 새로운 리소스가 생성되었습니다."),

    BAD_REQUEST(40000, "BAD_REQUEST", "올바르지 않은 요청입니다."),
    INVALID_VALUE(40001, "BAD_REQUEST", "요청 값이 올바르지 않습니다."),
    REQUIRE_VALUE(40002, "BAD_REQUEST", "필수 입력값이 누락되었습니다."),
    UNAUTHORIZED(40100, "UNAUTHORIZED", "인증 정보가 일치하지 않습니다."),
    INVALID_TOKEN(40101, "UNAUTHORIZED", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(40102, "UNAUTHORIZED", "만료된 토큰입니다."),
    EMPTY_TOKEN(40103, "UNAUTHORIZED", "토큰이 없습니다."),
    NOT_FOUND(40400, "NOT_FOUND", "리소스가 존재하지 않습니다."),
    ;

    private final Integer code;
    private final String httpStatus;
    private final String message;
}
