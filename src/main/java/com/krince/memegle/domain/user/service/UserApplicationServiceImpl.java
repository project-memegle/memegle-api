package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.service.AuthDomainService;
import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.User;

import com.krince.memegle.global.security.CustomUserDetails;
import com.krince.memegle.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserDomainService userDomainService;
    private final AuthDomainService authDomainService;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserInfoDto getUserInfo(CustomUserDetails userDetails) {
        return userDomainService.getUserInfoDtoFromId(userDetails.getId());
    }

    @Override
    @Transactional
    public void signUp(SignUpDto signUpDto) {
        userDomainService.validateDuplicateUser(signUpDto.getLoginId());

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        User user = User.of(signUpDto, encodedPassword);

        userDomainService.registUser(user);
    }

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        User user = userDomainService.getUserFromLoginId(signInDto.getLoginId());

        userDomainService.validatePassword(signInDto.getPassword(), user.getPassword());

        return jwtProvider.generateTokenDto(user.getId(), user.getRole());
    }

    @Override
    @Transactional
    public void changeNickname(CustomUserDetails userDetails, ChangeNicknameDto changeNicknameDto) {
        String nickname = changeNicknameDto.getNickname();
        User findUser = userDomainService.getUserFromId(userDetails.getId());

        userDomainService.validateDuplicateNickname(nickname);
        findUser.changeNickname(nickname);
    }

    @Override
    @Transactional
    public void dropUser(CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

        userDomainService.getUserFromId(userId);
        userDomainService.deleteFromId(userId);
        userDomainService.deleteSelfAuthenticationFromUserId(userId);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.of(changePasswordDto);
        authDomainService.validateAuthenticationCode(emailAuthenticationCodeDto);

        User findUser = userDomainService.getUserFromLoginId(changePasswordDto.getLoginId());
        String encodedPassword = passwordEncoder.encode(changePasswordDto.getPassword());
        findUser.changePassword(encodedPassword);
        userDomainService.registUser(findUser);
    }

    @Override
    public LoginIdDto getLoginId(FindLoginIdDto findLoginIdDto) {
        EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.of(findLoginIdDto);
        authDomainService.validateAuthenticationCode(emailAuthenticationCodeDto);
        User user = userDomainService.getUserFromEmail(findLoginIdDto.getEmail());

        return LoginIdDto.of(user.getLoginId());
    }
}
