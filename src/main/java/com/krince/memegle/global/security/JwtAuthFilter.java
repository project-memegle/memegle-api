package com.krince.memegle.global.security;

import com.krince.memegle.global.constant.Role;
import com.krince.memegle.global.exception.EmptyTokenException;
import com.krince.memegle.global.exception.JwtTokenInvalidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

import static com.krince.memegle.util.PermitAllUrlsUtil.*;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final String TOKEN_TYPE = "Bearer ";
    private final String HEADER_NAME = "Authorization";

    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = request.getHeader(HEADER_NAME);
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (isPermitAllURI(requestURI, getPermitAllUrls())) {
            filterChain.doFilter(request, response);

            return;
        }

        if (method.equals("GET") && isPermitAllURI(requestURI, getPermitAllGetUrls())) {
            filterChain.doFilter(request, response);

            return;
        }

        if (token == null) {
            throw new EmptyTokenException();
        }

        if (!token.startsWith(TOKEN_TYPE)) {
            throw new JwtTokenInvalidException();
        }

        String substringToken = token.substring(TOKEN_TYPE.length());

        if (!jwtProvider.isValidToken(substringToken)) {
            throw new JwtTokenInvalidException();
        }

        Role role = jwtProvider.getRole(substringToken);

        if (role.equals(Role.ROLE_USER)) {
            Long id = jwtProvider.getId(substringToken);
            CustomUserDetails userDetails = customUserDetailsService.loadUserById(id);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPermitAllURI(String requestURI, String[] permitAllUrls) {
        return Arrays.stream(permitAllUrls)
                .anyMatch(path -> pathMatcher.match(path, requestURI));
    }
}
