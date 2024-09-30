package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.global.Role;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.security.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtProvider jwtProvider;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        //given
        SignUpDto mockSignUpDto = mock(SignUpDto.class);
        User mockUser = mock(User.class);
        when(userRepository.existsByLoginId(any())).thenReturn(false);
        when(mockSignUpDto.getLoginId()).thenReturn("testLoginId1");
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$B/0v4wZRujJWa5SRIjUOmu/nIP9k2fmg4y23XbtzXT7c7gqqQOAPG");
        when(mockSignUpDto.getNickname()).thenReturn("testNickname1");
        when(userRepository.save(any())).thenReturn(mockUser);

        //when, then
        userService.signUp(mockSignUpDto);
    }

    @Test
    @DisplayName("중복된 회원은 예외를 발생시킨다")
    void signUpDuplicateUser() {
        //given
        SignUpDto mockSignUpDto = mock(SignUpDto.class);
        when(userRepository.existsByLoginId(any())).thenReturn(true);
        when(mockSignUpDto.getLoginId()).thenReturn("testLoginId1");

        //when, then
        assertThrows(DuplicateUserException.class, () -> userService.signUp(mockSignUpDto));
    }

    @Test
    @DisplayName("회원 로그인")
    void signIn() {
        //given
        User mockUser = mock(User.class);
        SignInDto mockSignInDto = mock(SignInDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("testLoginId1");
        when(mockSignInDto.getPassword()).thenReturn("testPassword1!");
        when(mockUser.getPassword()).thenReturn("$2a$10$B/0v4wZRujJWa5SRIjUOmu/nIP9k2fmg4y23XbtzXT7c7gqqQOAPG");
        when(mockUser.getRole()).thenReturn(Role.ROLE_USER);
        when(jwtProvider.createAccessToken(any(), any())).thenReturn("Bearer access_token");
        when(jwtProvider.createRefreshToken(any())).thenReturn("Bearer refresh_token");
        when(userRepository.findByLoginId(any())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        //when
        TokenDto tokenDto = userService.signIn(mockSignInDto);

        //then
        assertThat(tokenDto.getAccessToken()).isEqualTo("Bearer access_token");
        assertThat(tokenDto.getRefreshToken()).isEqualTo("Bearer refresh_token");
    }

    @Test
    @DisplayName("없는 아이디로 로그인을 시도하면 예외가 발생한다.")
    void signInWrongLoginId() {
        //given
        SignInDto mockSignInDto = mock(SignInDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("wrongLoginId");
        when(userRepository.findByLoginId(any())).thenReturn(Optional.empty());

        //when, then
        assertThrows(NoSuchElementException.class, () -> userService.signIn(mockSignInDto));
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인을 시도하면 예외를 발생시킨다.")
    void signInWrongPassword() {
        //given
        User mockUser = mock(User.class);
        SignInDto mockSignInDto = mock(SignInDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("testLoginId1");
        when(mockSignInDto.getPassword()).thenReturn("wrongPassword1!");
        when(mockUser.getPassword()).thenReturn("$2a$10$B/0v4wZRujJWa5SRIjUOmu/nIP9k2fmg4y23XbtzXT7c7gqqQOAPG");
        when(userRepository.findByLoginId(any())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        //when, then
        assertThrows(BadCredentialsException.class, () -> userService.signIn(mockSignInDto));
    }
}