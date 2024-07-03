package com.jdc.learning.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("com.jdc.learning.topic.json");
	}
	
	@Bean
	Queue transactionQueue() {
		return new Queue("com.jdc.learning.trx");
	}
	
	@Bean
	Binding transactionBinding() {
		return BindingBuilder.bind(transactionQueue()).to(exchange())
				.with("com.jdc.learning.message.transaction.*");
	}
	
	@Bean
	RabbitTemplate template(ConnectionFactory connectionFactory) {
		var template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jackson2JsonMessageConverter());
		return template;
	}
	
	@Bean
	Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
}
