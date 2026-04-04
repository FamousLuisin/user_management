package com.sea.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("REST API")
                .version("V1")
                .description("REST API with Java 8, Spring Boot 2.7.8, JPA, Hibernate, PostgreSQL, Docker, and OpenAPI")
                .license(new License()
                    .name("MIT License")
                    .url("https://mit-license.org/")
                )
            );
    }
}
