package com.krince.memegle.domain.auth.controller;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.service.AuthService;
import com.krince.memegle.global.exception.UndevelopedApiException;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponseCode;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis/client/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @GetMapping("/email")
    public ResponseEntity<ResponseCode> validateDuplicateMail(@RequestParam String email) {
        throw new UndevelopedApiException();
    }

    @Override
    @PostMapping("/email")
    public ResponseEntity<ResponseCode> validateEmailAuthenticationCode(@RequestBody @Valid EmailAuthenticationCodeDto emailAuthenticationCodeDto) {
        throw new UndevelopedApiException();
    }

    @Override
    @GetMapping("/login-id")
    public ResponseEntity<ResponseCode> validateDuplicateLoginId(String loginId) {
        throw new UndevelopedApiException();
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<ResponseCode> validateDuplicateNickname(String nickname) {
        throw new UndevelopedApiException();
    }

    @Override
    @PostMapping("/email/send")
    public ResponseEntity<ResponseCode> sendAuthenticationMail(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) throws MessagingException {
        authService.sendAuthenticationMail(userAuthenticationDto);

        SuccessResponseCode responseCode = SuccessResponseCode.NO_CONTENT;

        return ResponseEntity.status(responseCode.getHttpCode()).build();
    }
}
