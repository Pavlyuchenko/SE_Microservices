package com.se.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import com.se.inventory.models.InventorySingleton;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);

		/* Mock Data */
		InventorySingleton is = InventorySingleton.getInstance();
		is.createBook(123456, 3);
		is.createBook(42, 7);
		is.createBook(31415, 10);
	}

	// Set up to run on port 3002
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setPort(3002);
	}
}
