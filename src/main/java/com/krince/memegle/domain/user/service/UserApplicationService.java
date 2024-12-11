package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.global.security.CustomUserDetails;

public interface UserApplicationService {

    UserInfoDto getUserInfo(CustomUserDetails userDetails);

    void signUp(SignUpDto signUpDto);

    TokenDto signIn(SignInDto signInDto);

    void changeNickname(CustomUserDetails userDetails, ChangeNicknameDto changeNicknameDto);

    void dropUser(CustomUserDetails userDetails);

    void changePassword(ChangePasswordDto changePasswordDto);

    LoginIdDto getLoginId(FindLoginIdDto findLoginIdDto);
}
