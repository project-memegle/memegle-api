package com.krince.memegle.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseCode status = ResponseCode.FORBIDDEN;
        ExceptionResponse exceptionResponse = new ExceptionResponse(status);
        String responseBody = objectMapper.writeValueAsString(exceptionResponse);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.getHttpCode());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
