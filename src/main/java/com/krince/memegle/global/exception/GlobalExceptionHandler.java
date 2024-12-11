package com.krince.memegle.global.exception;

import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ExceptionResponseCode;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;
import java.util.stream.Collectors;

import static com.krince.memegle.global.response.ExceptionResponseCode.*;

@RestControllerAdvice
@Slf4j
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
        ExceptionResponseCode responseCode = INVALID_VALUE;
        ExceptionResponse exceptionResponse = new ExceptionResponse(responseCode, exceptionMessage);

        printExceptionInfo(exception);

        return ResponseEntity.status(responseCode.getHttpCode()).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        return generateExceptionResponse(exception, INVALID_VALUE);
    }

    //request 타입 불일치 예외
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestPartExceptionHandler(UnexpectedTypeException exception) {
        return generateExceptionResponse(exception, REQUIRE_VALUE);
    }

    // 올바르지 않은 request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        return generateExceptionResponse(exception, INVALID_VALUE);
    }

    //중복 회원 등록 시도 예외
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionResponse> duplicateUserExceptionHandler(DuplicateUserException exception) {
        return generateExceptionResponse(exception, DUPLICATE_USER);
    }

    //중복된 리소스 접근 시도
    @ExceptionHandler(DuplicationResourceException.class)
    public ResponseEntity<ExceptionResponse> DuplicationResourceExceptionHandler(DuplicationResourceException exception) {
        return generateExceptionResponse(exception, DUPLICATE_RESOURCE);
    }

    //필수 파라미터 누락 예외
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        String exceptionMessage = exception.getParameterName() + "은 필수 입력값입니다.";

        return generateMessageExceptionResponse(exception, BAD_REQUEST, exceptionMessage);
    }

    //비밀번호 오류
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialsExceptionHandler(BadCredentialsException exception) {
        return generateExceptionResponse(exception, INVALID_PASSWORD);
    }

    //없는 리소스 조회 시도 예외
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(NoSuchElementException exception) {
        return generateExceptionResponse(exception, NOT_FOUND_RESOURCE);
    }

    //메서드의 인자가 올바르지 않음
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return generateExceptionResponse(exception, INVALID_VALUE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedExceptionHandler(AccessDeniedException exception) {
        return generateExceptionResponse(exception, UNAUTHORIZED);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> iOExceptionHandler(IOException exception) {
        return generateExceptionResponse(exception, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UndevelopedApiException.class)
    private ResponseEntity<ExceptionResponse> undevelopedApiExceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, IMPLEMENTED);
    }

    @ExceptionHandler(InvalidAuthenticationCodeException.class)
    private ResponseEntity<ExceptionResponse> invalidAuthenticationCodeExceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, INVALID_AUTHENTICATION_CODE);
    }

    @ExceptionHandler(NoSuchAuthenticationCodeException.class)
    private ResponseEntity<ExceptionResponse> noSuchAuthenticationCodeExceptionHandler(Exception exception) {
        return generateExceptionResponse(exception, NO_SUCH_AUTHENTICATION_CODE);
    }

    private ResponseEntity<ExceptionResponse> generateExceptionResponse(Exception exception, ExceptionResponseCode status) {
        printExceptionInfo(exception);

        String exceptionMessage = getExistExceptionMessage(exception, status);
        ExceptionResponse exceptionResponse = new ExceptionResponse(status, exceptionMessage);

        return ResponseEntity.status(status.getHttpCode()).body(exceptionResponse);
    }

    private String getExistExceptionMessage(Exception exception, ExceptionResponseCode status) {
        if (Objects.isNull(exception.getMessage())) {
            return status.getMessage();
        }

        return exception.getMessage();
    }

    private ResponseEntity<ExceptionResponse> generateMessageExceptionResponse(Exception exception, ExceptionResponseCode status, String exceptionMessage) {
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
