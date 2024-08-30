package com.krince.memegle.domain.user.service;

import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.global.exception.DuplicateUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        //given
        SignUpDto mockSignUpDto = mock(SignUpDto.class);
        User mockUser = mock(User.class);
        when(userRepository.existsByLoginId(any())).thenReturn(false);
        when(mockSignUpDto.getLoginId()).thenReturn("testLoginId1");
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$B/0v4wZRujJWa5SRIjUOmu/nIP9k2fmg4y23XbtzXT7c7gqqQOAPG");
        when(mockSignUpDto.getName()).thenReturn("testName");
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
}