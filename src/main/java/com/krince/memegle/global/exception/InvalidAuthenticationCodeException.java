package com.krince.memegle.global.exception;

public class InvalidAuthenticationCodeException extends CustomException {

    private static final String defaultMessage = "이메일 인증코드가 일치하지 않습니다.";

    public InvalidAuthenticationCodeException() {
        super(defaultMessage);
    }

    public InvalidAuthenticationCodeException(String message) {
        super(message);
    }
}
