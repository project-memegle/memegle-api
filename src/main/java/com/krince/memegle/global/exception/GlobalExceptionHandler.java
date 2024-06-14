package com.krince.memegle.global.exception;

import com.krince.memegle.global.response.ExceptionResponseDto;
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
    public ResponseEntity<ExceptionResponseDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return generateExceptionResponse(exception, ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponseDto> missingServletRequestPartExceptionHandler(UnexpectedTypeException exception) {
        return generateExceptionResponse(exception, ResponseCode.REQUIRE_VALUE);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseDto> noSuchElementExceptionHandler(NoSuchElementException exception) {
        return generateExceptionResponse(exception, ResponseCode.NOT_FOUND_RESOURCE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponseDto> accessDeniedExceptionHandler(AccessDeniedException exception) {
        return generateExceptionResponse(exception, ResponseCode.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponseDto> iOExceptionHandler(IOException exception) {
        return generateExceptionResponse(exception, ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> exceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, ResponseCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponseDto> generateExceptionResponse(Exception exception, ResponseCode status) {
        StackTraceElement[] stackTrace = exception.getStackTrace();

        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println(exception.getMessage());
        System.out.println("==============================");
        System.out.println("==============================");

        for (StackTraceElement element : stackTrace) {
            System.out.println("exception: " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(status);

        return ResponseEntity.status(status.getHttpCode()).body(exceptionResponseDto);
    }
}