package com.krince.memegle.domain.auth.controller;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.customexception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.*;

@Tag(name = "인증", description = "인증 관련 API")
public interface AuthController {

    @Operation(summary = "본인 인증 이메일 전송", description = "본인 인증을 위한 인증 메일을 전송합니다.")
    @ApiResponse(description = "이메일 인증 전송 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "중복된 이메일", responseCode = "40002", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateUserExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> sendAuthenticationMail(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) throws MessagingException;

    @Operation(summary = "이메일 인증코드 검증", description = "이메일 인증코드와 서버에 저장된 인증코드의 일치유무를 검증합니다. 5분이상 지난 인증코드는 검증되지 않습니다.")
    @ApiResponse(description = "이메일 인증코드 검증 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "없는 이메일", responseCode = "40401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> validateEmailAuthenticationCode(@RequestBody @Valid EmailAuthenticationCodeDto emailAuthenticationCodeDto);
}
