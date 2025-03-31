package com.pragma.traceability.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi(@Value("${app-description}") String appDescription,
                                 @Value("${app-version}") String appVersion) {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Security Token", new SecurityScheme()
                                .name("Security Token")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("Access token for the API")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("Security Token"))
                .info(new Info()
                        .title("Traceability Microservice API")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("https://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                );
    }
}
