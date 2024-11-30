package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.ChangeNicknameDto;
import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.global.security.CustomUserDetails;

public interface UserService {

    UserInfoDto getUserInfo(CustomUserDetails userDetails);

    void signUp(SignUpDto signUpDto);

    TokenDto signIn(SignInDto signInDto);

    boolean changeNickname(CustomUserDetails userDetails, ChangeNicknameDto changeNicknameDto);
}
