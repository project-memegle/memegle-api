package com.krince.memegle.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.service.AuthServiceImpl;
import com.krince.memegle.global.constant.AuthenticationType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tags({
        @Tag("test"),
        @Tag("unitTest")
})
@WebMvcTest(value = AuthController.class)
@DisplayName("인증 컨트롤러 테스트(AuthController)")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthServiceImpl authService;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    @DisplayName("인증 이메일 전송 테스트")
    class SendAuthenticationMail {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithMockUser
            @DisplayName("인증 이메일 전송 테스트")
            void sendAuthenticationMail() throws Exception {
                //given
                String uri = "/apis/client/auth/email/send";
                UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
                when(mockUserAuthenticationDto.getUserName()).thenReturn("testUserName");
                when(mockUserAuthenticationDto.getEmail()).thenReturn("testEmail@gmail.com");
                when(mockUserAuthenticationDto.getAuthenticationType()).thenReturn(AuthenticationType.SIGN_UP);
                when(authService.sendAuthenticationMail(mockUserAuthenticationDto)).thenReturn("1Q2W3E");

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockUserAuthenticationDto))
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isNoContent());
                verify(authService, times(1)).sendAuthenticationMail(any());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @WithMockUser
            @DisplayName("필수값이 누락되면 예외를 발생시킨다.")
            void sendAuthenticationEmptyValue() throws Exception {
                //given
                String uri = "/apis/client/auth/email/send";
                HashMap<String, String> list = new HashMap<>();
                list.put("userNam", "testUserName");
                UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
                when(authService.sendAuthenticationMail(mockUserAuthenticationDto)).thenReturn("1Q2W3E");

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(list))
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("40001"))
                        .andExpect(jsonPath("$.status").value("Invalid Value"));
            }

            @Test
            @WithMockUser
            @DisplayName("request body 를 null 로 보낼 수 없다.")
            void sendAuthenticationNullBody() throws Exception {
                //given
                String uri = "/apis/client/auth/email/send";
                UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
                when(authService.sendAuthenticationMail(mockUserAuthenticationDto)).thenReturn("1Q2W3E");

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON_VALUE)
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("40001"))
                        .andExpect(jsonPath("$.status").value("Invalid Value"));
            }

            @Test
            @WithMockUser
            @DisplayName("유효하지 않은 값을 보내면 예외가 발생한다.")
            void sendAuthenticationInvalidValue() throws Exception {
                //given
                String uri = "/apis/client/auth/email/send";
                HashMap<String, String> list = new HashMap<>();
                list.put("userName", null);
                list.put("email", " ");
                UserAuthenticationDto mockUserAuthenticationDto = mock(UserAuthenticationDto.class);
                when(authService.sendAuthenticationMail(mockUserAuthenticationDto)).thenReturn("1Q2W3E");

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(list))
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value("40001"))
                        .andExpect(jsonPath("$.status").value("Invalid Value"));
            }
        }
    }
}