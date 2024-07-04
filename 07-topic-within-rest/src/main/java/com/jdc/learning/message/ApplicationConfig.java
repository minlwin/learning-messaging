package com.jdc.learning.message;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jdc.learning.message.model.BaseRepoImpl;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepoImpl.class)
public class ApplicationConfig {
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("com.jdc.demo");
	}
	
	@Bean
	Queue saleQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	Binding saleExchangeBinding() {
		return BindingBuilder.bind(saleQueue()).to(exchange()).with("sale");
	}

}
