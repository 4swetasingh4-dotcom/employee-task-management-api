package com.example.employeetask.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee & Task Management REST API")
                        .version("1.0.0")
                        .description("Backend REST API to manage company departments, employee profiles, and task assignments using Spring Boot, Spring Data JPA, Hibernate, and MySQL.")
                        .contact(new Contact()
                                .name("Backend Engineering Team")
                                .email("dev@company.com"))
                        .license(new License().name("Apache 2.0")));
    }
}

