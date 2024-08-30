package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.entity.User;

public interface UserService {

    public void signUp(SignUpDto signUpDto);
}
