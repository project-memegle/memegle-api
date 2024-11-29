package com.krince.memegle.global.exception;

public class EmptyTokenException extends CustomException {

    public EmptyTokenException() {
    }

    public EmptyTokenException(String message) {
        super(message);
    }

    public EmptyTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTokenException(Throwable cause) {
        super(cause);
    }
}
