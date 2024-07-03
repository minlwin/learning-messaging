package com.jdc.learning.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange("com.jdc.ps");
	}
	
	@Bean
	Queue subscriberQueue1() {
		return new Queue("subscriber1");
	}
	
	@Bean
	Queue subscriberQueue2() {
		return new Queue("subscriber2");
	}
	
	@Bean
	Binding binding1() {
		return BindingBuilder.bind(subscriberQueue1()).to(exchange());
	}
	
	@Bean
	Binding binding2() {
		return BindingBuilder.bind(subscriberQueue2()).to(exchange());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
