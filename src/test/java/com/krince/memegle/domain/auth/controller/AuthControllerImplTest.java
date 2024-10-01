package com.krince.memegle.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.service.AuthServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("인증 컨트롤러 테스트")
class AuthControllerImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthServiceImpl authService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("인증 이메일 전송 테스트")
    void sendAuthenticationMail() throws Exception {
        //given
        String uri = "/apis/client/auth/email/send";
        UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
        when(mockUserAuthenticationDto.getUserName()).thenReturn("testUserName");
        when(mockUserAuthenticationDto.getEmail()).thenReturn("testEmail@gmail.com");
        doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserAuthenticationDto)))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(authService, times(1)).sendAuthenticationMail(any());
    }
}