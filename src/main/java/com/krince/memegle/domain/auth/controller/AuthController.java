package com.krince.memegle.domain.auth.controller;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.service.AuthApplicationService;
import com.krince.memegle.global.exception.UndevelopedApiException;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponseCode;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.krince.memegle.global.response.SuccessResponseCode.*;

@RestController
@RequestMapping("/apis/client/auth")
@RequiredArgsConstructor
public class AuthController extends BaseAuthController {

    private final AuthApplicationService authApplicationService;

    @Override
    @GetMapping("/email")
    public ResponseEntity<SuccessResponseCode> validateDuplicateMail(@RequestParam String email) {
        authApplicationService.validateDuplicateMail(email);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @PostMapping("/email")
    public ResponseEntity<SuccessResponseCode> validateEmailAuthenticationCode(@RequestBody @Valid EmailAuthenticationCodeDto emailAuthenticationCodeDto) {
        authApplicationService.validateEmailAuthenticationCode(emailAuthenticationCodeDto);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @GetMapping("/login-id")
    public ResponseEntity<ResponseCode> validateDuplicateLoginId(@RequestParam String loginId) {
        authApplicationService.validateDuplicateLoginId(loginId);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<ResponseCode> validateDuplicateNickname(String nickname) {
        authApplicationService.validateDuplicateNickname(nickname);

        return ResponseEntity.status(NO_CONTENT.getHttpCode()).build();
    }

    @Override
    @PostMapping("/email/send")
    public ResponseEntity<ResponseCode> sendAuthenticationMail(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) throws MessagingException {
        authApplicationService.sendAuthenticationMail(userAuthenticationDto);

        SuccessResponseCode responseCode = SuccessResponseCode.NO_CONTENT;

        return ResponseEntity.status(responseCode.getHttpCode()).build();
    }
}
