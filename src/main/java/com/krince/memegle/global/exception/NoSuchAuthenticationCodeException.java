package com.krince.memegle.global.exception;

public class NoSuchAuthenticationCodeException extends CustomException {

    private static final String defaultMessage = "인증 이메일 정보가 없거나 만료기간이 초과되었습니다.";

    public NoSuchAuthenticationCodeException() {
        super(defaultMessage);
    }

    public NoSuchAuthenticationCodeException(String message) {
        super(message);
    }
}
