package com.krince.memegle.config;

import com.krince.memegle.global.security.*;
import com.krince.memegle.util.PermitAllUrlsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(this::authorizeRequests)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(this::stateless)
                .addFilterBefore(new JwtAuthFilter(jwtProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(this::authExceptionHandler)
                .getOrBuild();
    }

    private void stateless(SessionManagementConfigurer<HttpSecurity> sessionManagementConfigurer) {
        sessionManagementConfigurer.sessionCreationPolicy(STATELESS);
    }
    
    private void authExceptionHandler(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
    }

    private void authorizeRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationRequest) {
        authorizationRequest
                .requestMatchers(PermitAllUrlsUtil.getPermitAllUrls()).permitAll()
                .requestMatchers(GET, PermitAllUrlsUtil.getPermitAllGetUrls()).permitAll()
                .anyRequest().authenticated();
    }
}
