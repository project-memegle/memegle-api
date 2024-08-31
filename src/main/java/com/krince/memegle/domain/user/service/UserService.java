package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;

public interface UserService {

    void signUp(SignUpDto signUpDto);

    TokenDto signIn(SignInDto signInDto);
}
