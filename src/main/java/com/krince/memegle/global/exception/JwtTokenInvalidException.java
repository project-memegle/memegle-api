package com.krince.memegle.global.exception;

public class JwtTokenInvalidException extends CustomException {
    public JwtTokenInvalidException() {
    }

    public JwtTokenInvalidException(String message) {
        super(message);
    }

    public JwtTokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenInvalidException(Throwable cause) {
        super(cause);
    }
}
