package com.jdc.learning.message;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange("com.jdc.learning.direct");
	}
	
	@Bean
	Queue successQueue1() {
		return new AnonymousQueue();
	}
	
	@Bean
	Queue successQueue2() {
		return new AnonymousQueue();
	}

	@Bean
	Queue errorQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	Binding successBinding1() {
		return BindingBuilder.bind(successQueue1()).to(exchange()).with(Route.Success);
	}
	
	@Bean
	Binding successBinding2() {
		return BindingBuilder.bind(successQueue2()).to(exchange()).with(Route.Success);
	}
		
	@Bean
	Binding errorBinding() {
		return BindingBuilder.bind(errorQueue()).to(exchange()).with(Route.Error);
	}

	public enum Route {
		Success, Error
	}
}
