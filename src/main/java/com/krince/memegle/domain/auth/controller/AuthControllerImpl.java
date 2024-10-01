package com.krince.memegle.domain.auth.controller;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/client/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    @Override
    @PostMapping("/email/send")
    public ResponseEntity<ResponseCode> sendAuthenticationMail(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) {
        return null;
    }

    @Override
    @PostMapping("/email")
    public ResponseEntity<ResponseCode> validateEmailAuthenticationCode(@RequestBody @Valid EmailAuthenticationCodeDto emailAuthenticationCodeDto) {
        return null;
    }
}