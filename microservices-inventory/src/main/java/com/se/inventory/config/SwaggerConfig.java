package com.se.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI BookInventoryOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Book Inventory API")
                                                .description(
                                                                "Book Inventory API microservice for the Software Engineering class at Maastircht University. Made by Group 29.")
                                                .version("v1.0"));
        }
}
