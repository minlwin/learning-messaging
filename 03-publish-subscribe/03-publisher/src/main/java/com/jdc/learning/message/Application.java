package com.jdc.learning.message;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {
	
	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange("com.jdc.ps");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
