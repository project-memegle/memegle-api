package com.krince.memegle.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krince.memegle.global.response.ExceptionResponse;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.customexception.UnauthorizedExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseCode status = ResponseCode.UNAUTHORIZED;
        ExceptionResponse exceptionResponse = new UnauthorizedExceptionResponse(status);
        String responseBody = objectMapper.writeValueAsString(exceptionResponse);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.getHttpCode());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
