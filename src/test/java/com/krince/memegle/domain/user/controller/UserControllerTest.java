package com.krince.memegle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원 컨트롤러 테스트")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {
        //given
        String uri = "/apis/client/users/sign/up";
        SignUpDto mockSignUpDto = mock(SignUpDto.class);
        when(mockSignUpDto.getLoginId()).thenReturn("testLoginId1");
        when(mockSignUpDto.getPassword()).thenReturn("TestPassword1!");
        when(mockSignUpDto.getName()).thenReturn("testName");
        when(mockSignUpDto.getNickname()).thenReturn("testNickname");
        doNothing().when(userService).signUp(any());

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignUpDto)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 필수값 누락 테스트")
    void signUpEmptyValue() throws Exception {
        //given
        String uri = "/apis/client/users/sign/up";
        SignUpDto mockSignUpDto = mock(SignUpDto.class);
        doNothing().when(userService).signUp(any());

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignUpDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 올바르지 않은 request body")
    void signUpInvalidValue() throws Exception {
        //given
        String uri = "/apis/client/users/sign/up";
        SignUpDto mockSignUpDto = mock(SignUpDto.class);
        when(mockSignUpDto.getLoginId()).thenReturn("한글");
        when(mockSignUpDto.getPassword()).thenReturn("h");
        when(mockSignUpDto.getName()).thenReturn("");
        when(mockSignUpDto.getNickname()).thenReturn(" ");
        doNothing().when(userService).signUp(any());

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignUpDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}