package com.krince.memegle.config;

import com.krince.memegle.global.security.*;
import com.krince.memegle.util.PermitAllUrlsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static com.krince.memegle.global.constant.CorsArrowedUrl.*;
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
                .cors(this::corsConfigSetting)
                .sessionManagement(this::stateless)
                .addFilterBefore(new JwtAuthFilter(jwtProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(this::authExceptionHandler)
                .getOrBuild();
    }

    private void corsConfigSetting(CorsConfigurer<HttpSecurity> cors) {
        List<String> allowedOrigins = List.of(
                MEMEGLE_PROD_CLIENT_URL.getStringValue(),
                MEMEGLE_PROD_ADMIN_URL.getStringValue(),
                MEMEGLE_DEV_CLIENT_URL1.getStringValue(),
                MEMEGLE_DEV_CLIENT_URL1.getStringValue()
        );
        List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");

        CorsConfigurationSource corsConfigSource = request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(allowedOrigins);
            corsConfig.setAllowedMethods(allowedMethods);
            corsConfig.setAllowedHeaders(List.of("*"));
            corsConfig.setExposedHeaders(List.of("Authorization", "refresh-token"));
            return corsConfig;
        };

        cors.configurationSource(corsConfigSource);
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
