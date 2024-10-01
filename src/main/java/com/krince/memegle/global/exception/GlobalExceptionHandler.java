package com.krince.memegle.global.exception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.customexception.*;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        ResponseCode responseCode = INVALID_VALUE;
        InvalidValueExceptionResponse exceptionResponse = new InvalidValueExceptionResponse(responseCode, exceptionMessage);

        printExceptionInfo(exception);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        ResponseCode responseCode = INVALID_VALUE;
        InvalidValueExceptionResponse exceptionResponse = new InvalidValueExceptionResponse(responseCode);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    //request 타입 불일치 예외
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestPartExceptionHandler(UnexpectedTypeException exception) {
        return generateExceptionResponse(exception, REQUIRE_VALUE);
    }

    // 올바르지 않은 request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        ResponseCode responseCode = INVALID_VALUE;
        InvalidValueExceptionResponse exceptionResponse = new InvalidValueExceptionResponse(responseCode);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    //중복 회원 등록 시도 예외
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionResponse> duplicateUserExceptionHandler(DuplicateUserException exception) {
        ResponseCode responseCode = DUPLICATE_USER;
        DuplicateUserExceptionResponse exceptionResponse = new DuplicateUserExceptionResponse(responseCode);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    //필수 파라미터 누락 예외
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        String exceptionMessage = exception.getParameterName() + "은 필수 입력값입니다.";
        ResponseCode responseCode = BAD_REQUEST;
        BadRequestExceptionResponse exceptionResponse = new BadRequestExceptionResponse(responseCode, exceptionMessage);

        printExceptionInfo(exception);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    //비밀번호 오류
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsExceptionHandler(BadCredentialsException exception) {
        ResponseCode responseCode = INVALID_PASSWORD;
        InvalidPasswordExceptionResponse exceptionResponse = new InvalidPasswordExceptionResponse(responseCode, responseCode.getMessage());

        printExceptionInfo(exception);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    //없는 리소스 조회 시도 예외
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(NoSuchElementException exception) {
        ResponseCode responseCode = NOT_FOUND_RESOURCE;
        NotFoundResourceExceptionResponse exceptionResponse = new NotFoundResourceExceptionResponse(responseCode);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
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
        ResponseCode responseCode = INTERNAL_SERVER_ERROR;
        InternalServerErrorExceptionResponse exceptionResponse = new InternalServerErrorExceptionResponse(responseCode);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
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
