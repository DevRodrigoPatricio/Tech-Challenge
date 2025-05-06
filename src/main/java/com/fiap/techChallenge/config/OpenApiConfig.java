package com.fiap.techChallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Challenge - Fast Food API")
                        .version("1.0.0")
                        .description("Sistema de autoatendimento para lanchonete")
                        .contact(new Contact()
                                .name("Equipe Tech Challenge")));
    }
}