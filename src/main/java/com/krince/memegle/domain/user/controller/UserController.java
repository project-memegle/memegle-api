package com.krince.memegle.domain.user.controller;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.customexception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "회원", description = "회원 관련 API")
public interface UserController {

    @Operation(summary = "회원 등록", description = "회원가입을 진행합니다.")
    @ApiResponse(description = "회원 등록 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "없는 로그인 아이디", responseCode = "40102",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "틀린 비밀번호", responseCode = "40401",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InvalidPasswordExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> signUp(SignUpDto signUpDto);

    @Operation(summary = "회원 로그인", description = "로그인을 진행합니다.")
    @ApiResponse(description = "회원 로그인 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "필수값 누락", responseCode = "40000",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BadRequestExceptionResponse.class)))
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> signIn(SignInDto signInDto);
}
