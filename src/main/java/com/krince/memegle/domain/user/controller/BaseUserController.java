package com.krince.memegle.domain.user.controller;

import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원", description = "회원 관련 API")
public abstract class BaseUserController {

    @GetMapping
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponse(description = "회원 정보 조회 성공", responseCode = "20000")
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101", ref = "#/components/responses/40101")
    @ApiResponse(description = "토큰 정보 누락", responseCode = "40103", ref = "#/components/responses/40103")
    @ApiResponse(description = "만료된 토큰", responseCode = "40104", ref = "#/components/responses/40104")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<UserInfoDto>> getUserInfo(@Parameter(hidden = true) CustomUserDetails userDetails);

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "회원 정보를 삭제합니다.")
    @ApiResponse(description = "회원 탈퇴 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "인증 정보 불일치", responseCode = "40100", ref = "#/components/responses/40100")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> dropUser(@Parameter(hidden = true) CustomUserDetails userDetails);

    @PostMapping("/login-id")
    @Operation(summary = "회원 아이디 찾기", description = "이메일 인증 코드와 이메일로 해당 회원의 아이디를 조회합니다.")
    @ApiResponse(description = "회원 아이디 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "필수값 누락", responseCode = "40003", ref = "#/components/responses/40003")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<LoginIdDto>> getLoginId(@RequestBody @Valid FindLoginIdDto findLoginIdDto);

    @PutMapping("/nickname")
    @Operation(summary = "회원 닉네임 변경", description = "회원의 닉네임을 변경합니다.")
    @ApiResponse(description = "회원 닉네임 변경 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "필수값 누락", responseCode = "40003", ref = "#/components/responses/40003")
    @ApiResponse(description = "중복된 리소스", responseCode = "40004", ref = "#/components/responses/40004")
    @ApiResponse(description = "인증 정보 불일치", responseCode = "40100", ref = "#/components/responses/40100")
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101", ref = "#/components/responses/40101")
    @ApiResponse(description = "접근 권한 없음", responseCode = "40300", ref = "#/components/responses/40300")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> changeUserNickname(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ChangeNicknameDto changeNicknameDto
    );

    @PutMapping("/password")
    @Operation(summary = "회원 비밀번호 변경", description = "이메일 인증을 마친 회원의 비밀번호를 변경합니다.")
    @ApiResponse(description = "회원 비밀번호 변경 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "필수값 누락", responseCode = "40003", ref = "#/components/responses/40003")
    @ApiResponse(description = "이메일 인증코드 불일치", responseCode = "40105", ref = "#/components/responses/40105")
    @ApiResponse(description = "이메일 인증코드 정보 없음", responseCode = "40106", ref = "#/components/responses/40106")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto);

    @PostMapping("/sign/up")
    @Operation(summary = "회원 등록", description = "회원가입을 진행합니다.")
    @ApiResponse(description = "회원 등록 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "중복된 회원", responseCode = "40002", ref = "#/components/responses/40002")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> signUp(SignUpDto signUpDto);

    @PostMapping("/sign/in")
    @Operation(summary = "회원 로그인", description = "로그인을 진행합니다.")
    @ApiResponse(description = "회원 로그인 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청", responseCode = "40000", ref = "#/components/responses/40000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "틀린 비밀번호", responseCode = "40102", ref = "#/components/responses/40102")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> signIn(SignInDto signInDto);
}
