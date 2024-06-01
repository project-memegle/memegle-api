package com.krince.memegle.global.security;

import com.krince.memegle.global.Role;
import com.krince.memegle.global.exception.JwtTokenInvalidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private String TOKEN_TYPE = "Bearer ";
    private String HEADER_NAME = "Authorization";

    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HEADER_NAME);

        if (token == null) {
            filterChain.doFilter(request, response);

            return;
        }

        if (!token.startsWith(TOKEN_TYPE)) {
            throw new JwtTokenInvalidException();
        }

        String substringToken = token.substring(TOKEN_TYPE.length());

        if (!jwtProvider.isValidToken(substringToken)) {
            throw new JwtTokenInvalidException();
        }

        Role role = jwtProvider.getRole(substringToken);

        if (role.equals(Role.ROLE_ADMIN)) {
            Long id = jwtProvider.getId(substringToken);
            CustomUserDetails userDetails = customUserDetailsService.loadAdminById(id);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        if (role.equals(Role.ROLE_USER)) {
            Long id = jwtProvider.getId(substringToken);
            CustomUserDetails userDetails = customUserDetailsService.loadUserById(id);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
