package com.krince.memegle.domain.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("test")
@Tag("unitTest")
@WebMvcTest(NotificationController.class)
@DisplayName("알림 컨트롤러 테스트(NotificationController)")
class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Tag("develop")
    @Nested
    @WithMockUser
    @DisplayName("미열람 알림 유무 조회")
    class GetIsUnReadNotification {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("success")
            void success() throws Exception {
                //given
                String uri = "/apis/client/notifications/state";

                //when

                //then
                mockMvc.perform(get(uri)
                                .contentType(APPLICATION_JSON)
                                .with(csrf()))
                        .andExpect(status().isOk())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {

        }
    }
}