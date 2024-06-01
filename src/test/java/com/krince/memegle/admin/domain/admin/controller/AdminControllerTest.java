package com.krince.memegle.admin.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.admin.domain.admin.dto.request.RequestAdminLoginDto;
import com.krince.memegle.admin.domain.admin.service.AdminService;
import jakarta.transaction.Transactional;
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
@Transactional
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AdminService adminService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void login() throws Exception {
        //given
        RequestAdminLoginDto requestAdminLoginDto = mock(RequestAdminLoginDto.class);

        when(requestAdminLoginDto.getAdminId()).thenReturn("testId");
        when(requestAdminLoginDto.getPassword()).thenReturn("testPassword");
        when(adminService.login(any(RequestAdminLoginDto.class))).thenReturn("Bearer thisIsTestToken");

        //when, then
        mockMvc.perform(
                        post("/api/admin/sign/in")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAdminLoginDto))
                )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Authorization", "Bearer thisIsTestToken"));
    }
}