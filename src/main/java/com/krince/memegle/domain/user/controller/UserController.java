package com.krince.memegle.domain.user.controller;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.global.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원", description = "회원 관련 API")
public interface UserController {

    @Operation(summary = "회원 등록", description = "회원가입을 진행합니다.")
    @ApiResponse(description = "회원 등록 성공", responseCode = "204")
    ResponseEntity<ResponseCode> signUp(SignUpDto signUpDto);

    @Operation(summary = "회원 로그인", description = "로그인을 진행합니다.")
    @ApiResponse(description = "회원 로그인 성공", responseCode = "204")
    ResponseEntity<ResponseCode> signIn(SignInDto signInDto);
}
