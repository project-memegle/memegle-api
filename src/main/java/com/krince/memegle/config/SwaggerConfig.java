package com.krince.memegle.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerExceptionResponseConfig swaggerExceptionResponseConfig;

    @Bean
    public OpenAPI openAPI() {
        Components components = swaggerExceptionResponseConfig.getExceptionResponseComponents();

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        components.addSecuritySchemes("BearerAuth", securityScheme);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        Info info = new Info()
                .version("0.0.1")
                .title("memegle")
                .description("api 목록");

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(info);
    }
}
