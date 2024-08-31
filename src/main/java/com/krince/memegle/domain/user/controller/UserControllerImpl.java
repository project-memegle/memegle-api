package com.krince.memegle.domain.user.controller;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.service.UserService;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.krince.memegle.global.response.ResponseCode.*;

@RestController
@RequestMapping("/apis/client/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping("/sign/up")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseCode> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        userService.signUp(signUpDto);

        ResponseCode responseCode = NO_CONTENT;

        return ResponseEntity.status(responseCode.getHttpCode()).build();
    }

    @Override
    @PostMapping("/sign/in")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseCode> signIn(@RequestBody @Valid SignInDto signInDto) {
        TokenDto tokenDto = userService.signIn(signInDto);
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();
        ResponseCode responseCode = NO_CONTENT;

        return ResponseEntity.status(responseCode.getHttpCode())
                .header("Authorization", accessToken)
                .header("Refresh-Token", refreshToken)
                .build();
    }
}
