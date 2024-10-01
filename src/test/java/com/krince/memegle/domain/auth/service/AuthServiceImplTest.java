package com.krince.memegle.domain.auth.service;

import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.entity.EmailAuthentication;
import com.krince.memegle.domain.auth.repository.EmailAuthenticationRepository;
import com.krince.memegle.global.mail.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("인증 서비스 테스트")
class AuthServiceTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    EmailAuthenticationRepository emailAuthenticationRepository;

    @Mock
    EmailService emailService;

    @Test
    @DisplayName("인증 이메일 전송 테스트")
    void sendAuthenticationMail() throws Exception {
        //given
        UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
        when(mockUserAuthenticationDto.getEmail()).thenReturn("testUserName");
        when(mockUserAuthenticationDto.getUserName()).thenReturn("testEmail@gmail.com");
        when(emailService.sendUserAuthenticationEmail(any())).thenReturn("1Q2W3E");

        EmailAuthentication mockEmailAuthentication = mock(EmailAuthentication.class);
        when(emailAuthenticationRepository.save(any())).thenReturn(mockEmailAuthentication);

        //when
        authService.sendAuthenticationMail(mockUserAuthenticationDto);

        //then
        verify(emailService, times(1)).sendUserAuthenticationEmail(any());
        verify(emailAuthenticationRepository, times(1)).save(any());
    }
}