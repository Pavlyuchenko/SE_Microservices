package com.se.orders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI BookOrdersOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Book Orders API")
                                                .description(
                                                                "Book Orders API microservice for the Software Engineering class at Maastircht University. Made by Group 29.")
                                                .version("v1.0"));
        }
}
