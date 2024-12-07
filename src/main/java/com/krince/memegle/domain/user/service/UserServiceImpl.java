package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.auth.service.AuthService;
import com.krince.memegle.domain.user.dto.request.*;
import com.krince.memegle.domain.user.dto.response.LoginIdDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.dto.response.UserInfoDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.SelfAuthenticationRepository;
import com.krince.memegle.domain.user.repository.UserRepository;

import com.krince.memegle.global.constant.Role;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.exception.DuplicationResourceException;
import com.krince.memegle.global.security.CustomUserDetails;
import com.krince.memegle.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.krince.memegle.global.response.ExceptionResponseCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SelfAuthenticationRepository selfAuthenticationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final AuthService authService;

    @Override
    public UserInfoDto getUserInfo(CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

        return userRepository.findUserInfoDtoByUserId(userId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
        validateDuplicateUser(signUpDto.getLoginId());

        Role role = Role.ROLE_USER;
        User user = signUpDtoToUser(signUpDto, role);

        userRepository.save(user);
    }

    private void validateDuplicateUser(String loginId) {
        boolean isExistUser = userRepository.existsByLoginId(loginId);

        if (isExistUser) {
            throw new DuplicateUserException();
        }
    }

    private User signUpDtoToUser(SignUpDto signUpDto, Role role) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        return User.builder()
                .loginId(signUpDto.getLoginId())
                .password(encodedPassword)
                .nickname(signUpDto.getNickname())
                .role(role)
                .build();
    }

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        User user = getUserFromLoginId(signInDto.getLoginId());

        validatePassword(signInDto.getPassword(), user.getPassword());

        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        boolean isMatchedPassword = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!isMatchedPassword) {
            throw new BadCredentialsException(INVALID_PASSWORD.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean changeNickname(CustomUserDetails userDetails, ChangeNicknameDto changeNicknameDto) {
        Long userId = userDetails.getId();
        String nickname = changeNicknameDto.getNickname();
        User findUser = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        validateDuplicateNickname(nickname);
        findUser.changeNickname(nickname);

        return true;
    }

    private void validateDuplicateNickname(String nickname) {
        boolean isDuplicateNickname = userRepository.existsByNickname(nickname);

        if (isDuplicateNickname) {
            throw new DuplicationResourceException("이미 존재하는 닉네임입니다. 닉네임: " + nickname);
        }
    }

    @Override
    @Transactional
    public void dropUser(CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        userRepository.deleteById(userId);
        selfAuthenticationRepository.deleteByUserId(userId);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        authService.validateAuthenticationCode(
                changePasswordDto.getEmail(),
                changePasswordDto.getAuthenticationCode(),
                changePasswordDto.getAuthenticationType()
        );

        User findUser = getUserFromLoginId(changePasswordDto.getLoginId());
        String encodedPassword = passwordEncoder.encode(changePasswordDto.getPassword());

        findUser.changePassword(encodedPassword);

        userRepository.saveAndFlush(findUser);
    }

    @Override
    public LoginIdDto getLoginId(FindLoginIdDto findLoginIdDto) {
        String email = findLoginIdDto.getEmail();

        authService.validateAuthenticationCode(
                email,
                findLoginIdDto.getAuthenticationCode(),
                findLoginIdDto.getAuthenticationType()
        );

        User user = userRepository.findUserByEmail(email).orElseThrow(NoSuchElementException::new);

        return LoginIdDto.of(user.getLoginId());
    }


    private User getUserFromLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);
    }
}
