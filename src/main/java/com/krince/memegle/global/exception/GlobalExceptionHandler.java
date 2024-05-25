package com.krince.memegle.global.exception;

import com.krince.memegle.global.response.ExceptionResponseDto;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.println(fieldName + ": " + errorMessage);
            errors.put(fieldName, errorMessage);
        });

        ResponseCode status = ResponseCode.BAD_REQUEST;
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(status);

        return ResponseEntity.badRequest().body(exceptionResponseDto);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ExceptionResponseDto> missingServletRequestPartExceptionHandler(UnexpectedTypeException exception) {
        ResponseCode status = ResponseCode.REQUIRE_VALUE;
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(status);

        return ResponseEntity.badRequest().body(exceptionResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> exceptionHandler(Exception exception) {

        ResponseCode status = ResponseCode.INTERNAL_SERVER_ERROR;
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(status);

        return ResponseEntity.internalServerError().body(exceptionResponseDto);
    }
}
