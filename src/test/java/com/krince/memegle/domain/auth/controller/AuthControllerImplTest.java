package com.krince.memegle.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.service.AuthServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

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

    @Test
    @DisplayName("인증 이메일 전송에 필수값이 누락되면 예외를 발생시킨다.")
    void sendAuthenticationEmptyValue() throws Exception {
        //given
        String uri = "/apis/client/auth/email/send";
        HashMap<String, String> list = new HashMap<>();
        list.put("userNam", "testUserName");
        UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
        doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("40001"))
                .andExpect(jsonPath("$.status").value("Invalid Value"));
    }

    @Test
    @DisplayName("인증 이메일 전송에 request body 를 null 로 보낼 수 없다.")
    void sendAuthenticationNullBody() throws Exception {
        //given
        String uri = "/apis/client/auth/email/send";
        UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
        doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("40001"))
                .andExpect(jsonPath("$.status").value("Invalid Value"));
    }

    @Test
    @DisplayName("인증 이메일 전송에 유효하지 않은 값을 보내면 예외가 발생한다.")
    void sendAuthenticationInvalidValue() throws Exception {
        //given
        String uri = "/apis/client/auth/email/send";
        HashMap<String, String> list = new HashMap<>();
        list.put("userName", null);
        list.put("email", " ");
        UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
        doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("40001"))
                .andExpect(jsonPath("$.status").value("Invalid Value"));
    }
}