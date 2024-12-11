package com.krince.memegle.domain.auth.controller;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponseCode;
import com.krince.memegle.global.response.customexception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "인증", description = "인증 관련 API")
@RequestMapping("/apis/client/auth")
public abstract class BaseAuthController {

    @GetMapping("/email")
    @Operation(summary = "이메일 중복 검증", description = "해당 이메일이 이미 사용되는 이메일인지 검증합니다. 중복된 이메일이라면 예외를 반환합니다.")
    @ApiResponse(description = "이메일 중복 검증 검증 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "중복된 이메일", responseCode = "40002", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateUserExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    public abstract ResponseEntity<SuccessResponseCode> validateDuplicateMail(@RequestParam String email);

    @PostMapping("/email")
    @Operation(summary = "이메일 인증코드 검증(미구현 api)", description = "이메일 인증코드와 서버에 저장된 인증코드의 일치유무를 검증합니다. 5분이상 지난 인증코드는 검증되지 않습니다.")
    @ApiResponse(description = "이메일 인증코드 검증 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "없는 이메일", responseCode = "40401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    public abstract ResponseEntity<ResponseCode> validateEmailAuthenticationCode(@RequestBody @Valid EmailAuthenticationCodeDto emailAuthenticationCodeDto);

    @GetMapping("/login-id")
    @Operation(summary = "아이디 중복 검증(미구현 api)", description = "해당 아이디가 이미 사용되는 아이디인지 검증합니다. 중복된 아이디라면 예외를 반환합니다.")
    @ApiResponse(description = "아이디 중복 검증 검증 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "중복된 아이디", responseCode = "40002", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateUserExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    public abstract ResponseEntity<ResponseCode> validateDuplicateLoginId(@RequestParam String loginId);

    @GetMapping("/nickname")
    @Operation(summary = "닉네임 중복 검증(미구현 api)", description = "해당 닉네임이가 이미 사용되는 닉네임인지 검증합니다. 중복된 닉네임이라면 예외를 반환합니다.")
    @ApiResponse(description = "닉네임 중복 검증 검증 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "중복된 닉네임", responseCode = "40002", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateUserExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    public abstract ResponseEntity<ResponseCode> validateDuplicateNickname(@RequestParam String nickname);

    @PostMapping("/email/send")
    @Operation(summary = "본인 인증 이메일 전송", description = "본인 인증을 위한 인증 메일을 전송합니다.")
    @ApiResponse(description = "이메일 인증 전송 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청값", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "중복된 이메일", responseCode = "40002", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateUserExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    public abstract ResponseEntity<ResponseCode> sendAuthenticationMail(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) throws MessagingException;
}
