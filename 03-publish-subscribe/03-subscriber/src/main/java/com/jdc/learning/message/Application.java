package com.jdc.learning.message;

import org.springframework.amqp.core.AnonymousQueue;
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
	Queue autoDeleteQueue1() {
		return new AnonymousQueue();
	}
	
	@Bean
	Queue autoDeleteQueue2() {
		return new AnonymousQueue();
	}
	
	@Bean
	Binding binding1(FanoutExchange exchange, Queue autoDeleteQueue1) {
		return BindingBuilder.bind(autoDeleteQueue1).to(exchange);
	}
	
	@Bean
	Binding binding2(FanoutExchange exchange, Queue autoDeleteQueue2) {
		return BindingBuilder.bind(autoDeleteQueue2).to(exchange);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
