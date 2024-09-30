package com.krince.memegle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.krince.memegle.global.response.ResponseCode.*;
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
        when(mockSignUpDto.getNickname()).thenReturn(" ");
        doNothing().when(userService).signUp(any());

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignUpDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인")
    void signIn() throws Exception {
        //given
        String uri = "/apis/client/users/sign/in";
        SignInDto mockSignInDto = mock(SignInDto.class);
        TokenDto mockTokenDto = mock(TokenDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("testLoginId1");
        when(mockSignInDto.getPassword()).thenReturn("TestPassword1!");
        when(mockTokenDto.getAccessToken()).thenReturn("Bearer access_token");
        when(mockTokenDto.getRefreshToken()).thenReturn("Bearer refresh_token");
        when(userService.signIn(any())).thenReturn(mockTokenDto);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignInDto)))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().exists("Refresh-Token"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인 - request 누락")
    void signInInvalidValue() throws Exception {
        //given
        String uri = "/apis/client/users/sign/in";
        Map<String, String> wrongRequest = new HashMap<>();
        wrongRequest.put("wrongKey", "wrongValue");

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(INVALID_VALUE.getCode()))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인 - 없는 아이디")
    void signInInvalidId() throws Exception {
        //given
        String uri = "/apis/client/users/sign/in";
        SignInDto mockSignInDto = mock(SignInDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("wrongId1");
        when(mockSignInDto.getPassword()).thenReturn("TestPassword1!");
        when(userService.signIn(any())).thenThrow(NoSuchElementException.class);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignInDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(NOT_FOUND_RESOURCE.getCode()))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인 - 틀린 비밀번호")
    void signInInvalidPassword() throws Exception {
        //given
        String uri = "/apis/client/users/sign/in";
        SignInDto mockSignInDto = mock(SignInDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("testLoginId1");
        when(mockSignInDto.getPassword()).thenReturn("wrongPassword1!");
        when(userService.signIn(any())).thenThrow(BadCredentialsException.class);

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignInDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value(INVALID_PASSWORD.getCode()))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인 - 잘못된 request")
    void signInInvalidRequestBody() throws Exception {
        //given
        String uri = "/apis/client/users/sign/in";
        SignInDto mockSignInDto = mock(SignInDto.class);
        when(mockSignInDto.getLoginId()).thenReturn("testLogin Id1한 글한글한 글");
        when(mockSignInDto.getPassword()).thenReturn("TestPassword1!한글 띄어쓰기 엄청나게 긴 비밀번호");

        //when, then
        mockMvc.perform(post(uri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockSignInDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(INVALID_VALUE.getCode()))
                .andDo(print());
    }
}