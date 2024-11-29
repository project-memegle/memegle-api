package com.krince.memegle.global.mail;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest"),
        @Tag("mockTest")
})
@DisplayName("이메일 서비스 테스트(EmailService)")
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    EmailServiceImpl emailService;

    @Mock
    JavaMailSenderImpl javaMailSender;

    @Test
    @DisplayName("이메일 전송")
    void sendUserAuthenticationEmail() throws Exception {
        //given
        String email = "ssk0080@gmail.com";
        MimeMessage mockMimeMailMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mockMimeMailMessage);

        //when
        String authenticationCode = emailService.sendUserAuthenticationEmail(email);

        //then
        assertThat(authenticationCode.length()).isEqualTo(6);
        verify(javaMailSender, times(1)).send(mockMimeMailMessage);
        verify(javaMailSender, times(1)).createMimeMessage();
    }
}