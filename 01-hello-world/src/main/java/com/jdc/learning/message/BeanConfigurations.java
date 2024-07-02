package com.jdc.learning.message;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurations {

	@Bean
	Queue queue() {
		return new Queue("hello");
	}

}
