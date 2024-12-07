package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    UserInfoDto getUserInfo(CustomUserDetails userDetails);

    void signUp(SignUpDto signUpDto);

    TokenDto signIn(SignInDto signInDto);

    boolean changeNickname(CustomUserDetails userDetails, ChangeNicknameDto changeNicknameDto);

    void dropUser(CustomUserDetails userDetails);

    void changePassword(ChangePasswordDto changePasswordDto);

    LoginIdDto getLoginId(FindLoginIdDto findLoginIdDto);
}
