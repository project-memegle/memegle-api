package com.krince.memegle.global.exception;

public class DuplicationResourceException extends RuntimeException {
    private static final String defaultMessage = "이미 존재하는 리소스입니다.";

    public DuplicationResourceException() {
        super(defaultMessage);
    }

    public DuplicationResourceException(String message) {
        super(message);
    }
}
