package com.krince.memegle.global.exception;

public class UndevelopedApiException extends RuntimeException {

    private static final String defaultMessage = "미구현 api 입니다.";

    public UndevelopedApiException() {
        super(defaultMessage);
    }

    public UndevelopedApiException(String message) {
        super(message);
    }
}
