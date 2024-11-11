package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.UserRepository;

import com.krince.memegle.global.constant.Role;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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
        User user = userRepository.findByLoginId(signInDto.getLoginId()).orElseThrow();

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
            throw new BadCredentialsException(ResponseCode.INVALID_PASSWORD.getMessage());
        }
    }
}
