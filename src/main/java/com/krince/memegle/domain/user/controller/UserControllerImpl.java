package com.krince.memegle.domain.user.controller;

import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.service.UserService;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.response.SuccessResponseCode;
import com.krince.memegle.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.krince.memegle.global.response.SuccessResponseCode.NO_CONTENT;
import static com.krince.memegle.global.response.SuccessResponseCode.OK;

@RestController
@RequestMapping("/apis/client/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<UserInfoDto>> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInfoDto userInfoDto = userService.getUserInfo(userDetails);
        SuccessResponseCode responseCode = OK;
        SuccessResponse<UserInfoDto> responseBody = new SuccessResponse<>(responseCode, userInfoDto);

        return ResponseEntity.status(responseCode.getHttpCode()).body(responseBody);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<ResponseCode> dropUser(CustomUserDetails userDetails) {
        userService.dropUser(userDetails);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @PostMapping("/login-id")
    public ResponseEntity<SuccessResponse<LoginIdDto>> getLoginId(@RequestBody @Valid FindLoginIdDto findLoginIdDto) {
        LoginIdDto loginIdDto = userService.getLoginId(findLoginIdDto);
        SuccessResponseCode responseCode = OK;
        SuccessResponse<LoginIdDto> responseBody = new SuccessResponse<>(responseCode, loginIdDto);

        return ResponseEntity.status(responseCode.getHttpCode()).body(responseBody);
    }

    @Override
    @PutMapping("/nickname")
    public ResponseEntity<ResponseCode> changeUserNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ChangeNicknameDto changeNicknameDto
    ) {
        userService.changeNickname(userDetails, changeNicknameDto);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @PutMapping("/password")
    public ResponseEntity<ResponseCode> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        userService.changePassword(changePasswordDto);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @PostMapping("/sign/up")
    public ResponseEntity<ResponseCode> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        userService.signUp(signUpDto);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @PostMapping("/sign/in")
    public ResponseEntity<ResponseCode> signIn(@RequestBody @Valid SignInDto signInDto) {
        TokenDto tokenDto = userService.signIn(signInDto);
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        return ResponseEntity.status(NO_CONTENT.getHttpCode())
                .header("Authorization", accessToken)
                .header("Refresh-Token", refreshToken)
                .build();
    }
}
