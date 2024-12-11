package com.krince.memegle.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.auth.dto.EmailAuthenticationCodeDto;
import com.krince.memegle.domain.auth.dto.UserAuthenticationDto;
import com.krince.memegle.domain.auth.service.AuthApplicationServiceImpl;
import com.krince.memegle.global.constant.AuthenticationType;
import com.krince.memegle.global.exception.DuplicateUserException;
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

@Tag("test")
@Tag("unitTest")
@WebMvcTest(value = AuthController.class)
@DisplayName("인증 컨트롤러 테스트(AuthController)")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthApplicationServiceImpl authService;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    @DisplayName("중복 이메일 검증 테스트")
    @Tag("develop")
    class ValidateDuplicateMail {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithMockUser
            @DisplayName("중복된 이메일 없음")
            void success() throws Exception {
                //given
                String uri = "/apis/client/auth/email";
                doNothing().when(authService).validateDuplicateMail("test@test.com");

                //when, then
                mockMvc.perform(get(uri)
                                .contentType(APPLICATION_JSON)
                                .param("email", "test@test.com"))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @WithMockUser
            @DisplayName("중복된 이메일 있음")
            void fail() throws Exception {
                //given
                String uri = "/apis/client/auth/email";
                doThrow(DuplicateUserException.class).when(authService).validateDuplicateMail("test@test.com");

                //when, then
                mockMvc.perform(get(uri)
                                .contentType(APPLICATION_JSON)
                                .param("email", "test@test.com"))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.code").value(40002));
            }
        }


    }

    @Tag("develop")
    @Nested
    @DisplayName("이메일 인증코드 검증")
    class ValidateEmailAuthenticationCode {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithMockUser
            @DisplayName("success")
            void success() throws Exception {
                //given
                String uri = "/apis/client/auth/email";
                EmailAuthenticationCodeDto emailAuthenticationCodeDto = EmailAuthenticationCodeDto.builder()
                        .email("test@test.com")
                        .authenticationCode("1Q2W3E")
                        .authenticationType(AuthenticationType.ID)
                        .build();
                doNothing().when(authService).validateEmailAuthenticationCode(any());

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emailAuthenticationCodeDto))
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {
        }
    }

    @Nested
    @DisplayName("중복 닉네임 검증 테스트")
    @Tag("develop")
    @Tag("target")
    class ValidateDuplicateNickname {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithMockUser
            @DisplayName("성공")
            void success() throws Exception {
                //given
                String uri = "/apis/client/auth/nickname";

                //when

                //then
                mockMvc.perform(get(uri)
                                .contentType(APPLICATION_JSON)
                                .param("nickname", "nickname"))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

        }


    }

    @Nested
    @DisplayName("중복 로그인 아이디 검증 테스트")
    @Tag("develop")
    class ValidateDuplicateLoginId {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithMockUser
            @DisplayName("성공")
            void success() throws Exception {
                //given
                String uri = "/apis/client/auth/login-id";

                //when, then
                mockMvc.perform(get(uri)
                                .contentType(APPLICATION_JSON)
                                .param("loginId", "test@test.com"))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

        }


    }

    @Tag("unitTest")
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
                doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

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
                doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

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
                doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

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
                doNothing().when(authService).sendAuthenticationMail(mockUserAuthenticationDto);

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