package com.krince.memegle.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.domain.user.dto.request.ChangeNicknameDto;
import com.krince.memegle.domain.user.dto.request.SignInDto;
import com.krince.memegle.domain.user.dto.request.SignUpDto;
import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.domain.user.service.UserService;
import com.krince.memegle.global.exception.DuplicateUserException;
import com.krince.memegle.global.security.JwtProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static com.krince.memegle.global.response.ResponseCode.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("test")
@WebMvcTest(value = UserController.class)
@DisplayName("회원 컨트롤러 테스트(UserController)")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtProvider jwtProvider;

    @Tag("develop")
    @Nested
    @DisplayName("회원 정보 조회")
    class GetUserInfo {

        @Test
        @WithMockUser
        @DisplayName("성공")
        void Success() throws Exception {
            Assertions.assertThat(true).isFalse();
        }

        @Nested
        @DisplayName("실패")
        class SignUpFail {
        }
    }

    @Tag("develop")
    @Nested
    @DisplayName("회원 탈퇴")
    class DropUser {

        @Test
        @WithMockUser
        @DisplayName("성공")
        void Success() throws Exception {
            Assertions.assertThat(true).isFalse();
        }

        @Nested
        @DisplayName("실패")
        class SignUpFail {
        }
    }

    @Tag("develop")
    @Nested
    @DisplayName("회원 아이디 찾기")
    class GetLoginId {

        @Test
        @WithMockUser
        @DisplayName("성공")
        void Success() throws Exception {
            Assertions.assertThat(true).isFalse();
        }

        @Nested
        @DisplayName("실패")
        class SignUpFail {
        }
    }

    @Tag("develop")
    @Nested
    @DisplayName("회원 닉네임 수정")
    class ChangeUserNickname {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @WithMockUser
            @DisplayName("success")
            void success() throws Exception {
                //given
                String uri = "/apis/client/users/nickname";
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("test").build();
                when(userService.changeNickname(any(), any())).thenReturn(true);

                //when, then
                mockMvc.perform(put(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(changeNicknameDto))
                                .header("Authorization", "Bearer testToken")
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @WithMockUser
            @DisplayName("중복된 닉네임은 예외를 반환한다.")
            void duplicateNickname() throws Exception {
                //given
                String uri = "/apis/client/users/nickname";
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("test").build();
                when(userService.changeNickname(any(), any())).thenThrow(DuplicateUserException.class);

                //when, then
                mockMvc.perform(put(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(changeNicknameDto))
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }

            @Test
            @WithMockUser
            @DisplayName("회원 정보가 일치하지 않으면 예외를 반환한다.")
            void unregisteredUser() throws Exception {
                //given
                String uri = "/apis/client/users/nickname";
                ChangeNicknameDto changeNicknameDto = ChangeNicknameDto.builder().nickname("test").build();
                when(userService.changeNickname(any(), any())).thenThrow(NoSuchElementException.class);

                //when, then
                mockMvc.perform(put(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(changeNicknameDto))
                                .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Tag("develop")
    @Nested
    @DisplayName("회원 비밀번호 변경")
    class ChangePassword {

        @Test
        @WithMockUser
        @DisplayName("성공")
        void Success() throws Exception {
            Assertions.assertThat(true).isFalse();
        }

        @Nested
        @DisplayName("실패")
        class SignUpFail {
        }
    }

    @Tag("unitTest")
    @Nested
    @DisplayName("회원가입")
    class SignUp {

        @Test
        @WithMockUser
        @DisplayName("성공")
        void signUpSuccess() throws Exception {
            //given
            String uri = "/apis/client/users/sign/up";
            SignUpDto signUpDto = SignUpDto.builder()
                    .loginId("testloginid1")
                    .password("testpassword1!")
                    .nickname("testNickname")
                    .build();
            doNothing().when(userService).signUp(any());

            //when, then
            mockMvc.perform(post(uri)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpDto))
                            .with(csrf()))
                    .andExpect(status().isNoContent())
                    .andDo(print());
        }

        @Tag("unitTest")
        @Nested
        @DisplayName("실패")
        class SignUpFail {

            @Test
            @WithMockUser
            @DisplayName("중복 회원")
            void duplicateUser() throws Exception {
                //given
                String uri = "/apis/client/users/sign/up";
                SignUpDto signUpDto = SignUpDto.builder()
                        .loginId("testloginid1")
                        .password("testpassword1!")
                        .nickname("testNickname")
                        .build();
                doThrow(DuplicateUserException.class).when(userService).signUp(any());

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpDto))
                                .with(csrf()))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value(DUPLICATE_USER.getMessage()))
                        .andExpect(jsonPath("$.code").value(DUPLICATE_USER.getCode()))
                        .andDo(print());
            }
        }
    }

    @Tag("unitTest")
    @Nested
    @DisplayName("회원 로그인")
    class SignIn {

        @Test
        @WithMockUser
        @DisplayName("성공")
        void signInSuccess() throws Exception {
            //given
            String uri = "/apis/client/users/sign/in";
            SignInDto mockSignInDto = mock(SignInDto.class);
            TokenDto mockTokenDto = mock(TokenDto.class);
            when(mockSignInDto.getLoginId()).thenReturn("testloginid1");
            when(mockSignInDto.getPassword()).thenReturn("TestPassword1!");
            when(mockTokenDto.getAccessToken()).thenReturn("Bearer access_token");
            when(mockTokenDto.getRefreshToken()).thenReturn("Bearer refresh_token");
            when(userService.signIn(any())).thenReturn(mockTokenDto);

            //when, then
            mockMvc.perform(post(uri)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(mockSignInDto))
                            .with(csrf()))
                    .andExpect(status().isNoContent())
                    .andExpect(header().exists("Authorization"))
                    .andExpect(header().exists("Refresh-Token"))
                    .andDo(print());
        }

        @Tag("unitTest")
        @Nested
        @DisplayName("실패")
        class SignUpFail {

            @Test
            @WithMockUser
            @DisplayName("없는 아이디")
            void invalidId() throws Exception {
                //given
                String uri = "/apis/client/users/sign/in";
                SignInDto mockSignInDto = mock(SignInDto.class);
                when(mockSignInDto.getLoginId()).thenReturn("wrongid1");
                when(mockSignInDto.getPassword()).thenReturn("TestPassword1!");
                when(userService.signIn(any())).thenThrow(NoSuchElementException.class);

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockSignInDto))
                                .with(csrf()))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("code").value(NOT_FOUND_RESOURCE.getCode()))
                        .andDo(print());
            }

            @Test
            @WithMockUser
            @DisplayName("틀린 비밀번호")
            void invalidPassword() throws Exception {
                //given
                String uri = "/apis/client/users/sign/in";
                SignInDto mockSignInDto = mock(SignInDto.class);
                when(mockSignInDto.getLoginId()).thenReturn("testloginid1");
                when(mockSignInDto.getPassword()).thenReturn("wrongPassword1!");
                when(userService.signIn(any())).thenThrow(BadCredentialsException.class);

                //when, then
                mockMvc.perform(post(uri)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockSignInDto))
                                .with(csrf()))
                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("code").value(INVALID_PASSWORD.getCode()))
                        .andDo(print());
            }
        }
    }
}

