package com.krince.memegle.global.exception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return generateExceptionResponse(exception, ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestPartExceptionHandler(UnexpectedTypeException exception) {
        return generateExceptionResponse(exception, ResponseCode.REQUIRE_VALUE);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(NoSuchElementException exception) {
        return generateExceptionResponse(exception, ResponseCode.NOT_FOUND_RESOURCE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedExceptionHandler(AccessDeniedException exception) {
        return generateExceptionResponse(exception, ResponseCode.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> iOExceptionHandler(IOException exception) {
        return generateExceptionResponse(exception, ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, ResponseCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> generateExceptionResponse(Exception exception, ResponseCode status) {
        StackTraceElement[] stackTrace = exception.getStackTrace();

        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println(exception.getMessage());
        System.out.println("==============================");
        System.out.println("==============================");

        for (StackTraceElement element : stackTrace) {
            System.out.println("exception: " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(status);

        return ResponseEntity.status(status.getHttpCode()).body(exceptionResponse);
    }
}
