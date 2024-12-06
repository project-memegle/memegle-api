package com.krince.memegle.domain.user.controller;

import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.response.customexception.*;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "회원", description = "회원 관련 API")
public interface UserController {

    @GetMapping
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponse(description = "회원 정보 조회 성공", responseCode = "20000")
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidTokenExceptionResponse.class)))
    @ApiResponse(description = "토큰 정보 누락", responseCode = "40103", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmptyTokenExceptionResponse.class)))
    @ApiResponse(description = "만료된 토큰", responseCode = "40104", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExpiredTokenExceptionResponse.class)))
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<SuccessResponse<UserInfoDto>> getUserInfo(@Parameter(hidden = true) CustomUserDetails userDetails);

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "회원 정보를 삭제합니다.")
    @ApiResponse(description = "회원 탈퇴 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "인증 정보 불일치", responseCode = "40100", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UnauthorizedExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> dropUser(@Parameter(hidden = true) CustomUserDetails userDetails);

    @PostMapping("/login-id")
    @Operation(summary = "회원 아이디 찾기", description = "이메일 인증 코드와 이메일로 해당 회원의 아이디를 조회합니다.")
    @ApiResponse(description = "회원 아이디 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "없는 이메일, 인증코드", responseCode = "40401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<SuccessResponse<LoginIdDto>> getLoginId(@RequestBody @Valid FindLoginIdDto findLoginIdDto);

    @PutMapping("/nickname")
    @Operation(summary = "회원 닉네임 변경", description = "회원의 닉네임을 변경합니다.")
    @ApiResponse(description = "회원 닉네임 변경 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "중복된 리소스", responseCode = "40004", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateResourceExceptionResponse.class)))
    @ApiResponse(description = "인증 정보 불일치", responseCode = "40100", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UnauthorizedExceptionResponse.class)))
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidTokenExceptionResponse.class)))
    @ApiResponse(description = "접근 권한 없음", responseCode = "40300", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ForbiddenUserExceptionResponse.class)))
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotFoundResourceExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> changeUserNickname(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ChangeNicknameDto changeNicknameDto
    );

    @PutMapping("/password")
    @Operation(summary = "회원 비밀번호 변경", description = "이메일 인증을 마친 회원의 비밀번호를 변경합니다.")
    @ApiResponse(description = "회원 비밀번호 변경 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "필수값 누락", responseCode = "40003", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = RequireValueExceptionResponse.class)))
    @ApiResponse(description = "이메일 인증코드 불일치", responseCode = "40105", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidAuthenticationCodeExceptionResponse.class)))
    @ApiResponse(description = "이메일 인증코드 정보 없음", responseCode = "40106", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = NoSuchAuthenticationCodeExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto);

    @PostMapping("/sign/up")
    @Operation(summary = "회원 등록", description = "회원가입을 진행합니다.")
    @ApiResponse(description = "회원 등록 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "중복 회원", responseCode = "40002", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DuplicateUserExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> signUp(SignUpDto signUpDto);

    @PostMapping("/sign/in")
    @Operation(summary = "회원 로그인", description = "로그인을 진행합니다.")
    @ApiResponse(description = "회원 로그인 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "필수값 누락", responseCode = "40000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BadRequestExceptionResponse.class)))
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidValueExceptionResponse.class)))
    @ApiResponse(description = "틀린 비밀번호", responseCode = "40102", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidPasswordExceptionResponse.class)))
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InternalServerErrorExceptionResponse.class)))
    ResponseEntity<ResponseCode> signIn(SignInDto signInDto);
}
