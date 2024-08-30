package com.krince.memegle.global.exception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.krince.memegle.global.response.ResponseCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //@Valid 검증 실패 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getBindingResult()
                .getAllErrors()
                .stream().map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return fieldName + ": " + message;
                })
                .collect(Collectors.joining());

        return generateMessageExceptionResponse(exception, INVALID_VALUE, exceptionMessage);
    }

    //request 타입 불일치 예외
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestPartExceptionHandler(UnexpectedTypeException exception) {
        return generateExceptionResponse(exception, REQUIRE_VALUE);
    }

    //중복 회원 등록 시도 예외
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionResponse> duplicateUserExceptionHandler(DuplicateUserException exception) {
        return generateExceptionResponse(exception, DUPLICATE_USER);
    }

    //필수 파라미터 누락 예외
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        String exceptionMessage = exception.getParameterName() + "은 필수 입력값입니다.";
        return generateMessageExceptionResponse(exception, BAD_REQUEST, exceptionMessage);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(NoSuchElementException exception) {
        return generateExceptionResponse(exception, NOT_FOUND_RESOURCE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedExceptionHandler(AccessDeniedException exception) {
        return generateExceptionResponse(exception, FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> iOExceptionHandler(IOException exception) {
        return generateExceptionResponse(exception, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> generateExceptionResponse(Exception exception, ResponseCode status) {
        printExceptionInfo(exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(status);

        return ResponseEntity.status(status.getHttpCode()).body(exceptionResponse);
    }

    private ResponseEntity<ExceptionResponse> generateMessageExceptionResponse(Exception exception, ResponseCode status, String exceptionMessage) {
        printExceptionInfo(exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(status, exceptionMessage);

        return ResponseEntity.status(status.getHttpCode()).body(exceptionResponse);
    }

    private void printExceptionInfo(Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();

        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println(exception.getClass());
        System.out.println(exception.getMessage());
        System.out.println("==============================");
        System.out.println("==============================");

        for (StackTraceElement element : stackTrace) {
            System.out.println("exception: " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }
    }
}
