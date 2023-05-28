package com.se.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import com.se.orders.controllers.OrdersGetController;
import com.se.orders.models.ActiveOrdersSingleton;
import com.se.orders.models.Order;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class OrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersApplication.class, args);

		ActiveOrdersSingleton aos = ActiveOrdersSingleton.getInstance();
		Order order1 = new Order(new ArrayList<Integer>(Arrays.asList(123456, 42, 31415)),
				new ArrayList<Integer>(Arrays.asList(5, 2, 3)));
	}

	// Set up to run on port 3003
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setPort(3003);
	}
}
