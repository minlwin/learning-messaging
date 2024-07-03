package com.jdc.learning.message;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("com.jdc.learning.topic");
	}
	
	@Bean
	Queue transactionQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	Queue accountingQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	Binding transactionBinding() {
		return BindingBuilder.bind(transactionQueue()).to(exchange()).with("#.transaction.*");
	}
	
	@Bean
	Binding accountingBinding() {
		return BindingBuilder.bind(accountingQueue()).to(exchange()).with("#.accounting.*");
	}
}
