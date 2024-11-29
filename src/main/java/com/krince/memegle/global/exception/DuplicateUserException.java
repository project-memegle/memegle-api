package com.krince.memegle.global.exception;

public class DuplicateUserException extends RuntimeException {
    private static final String defaultMessage = "이미 존재하는 회원입니다.";

    public DuplicateUserException() {
        super(defaultMessage);
    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
