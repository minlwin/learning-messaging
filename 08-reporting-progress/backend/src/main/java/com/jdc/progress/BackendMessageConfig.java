package com.jdc.progress;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

@EnableAsync
@Configuration
public class BackendMessageConfig {
	
	@Value("${app.esc.state-exchange}")
	private String stateExchangeId;

	@Bean
	TopicExchange uploadStateExchange() {
		return new TopicExchange(stateExchangeId);
	}
	
	@Bean
	Queue validationQueue() {
		return new Queue(UploadState.Validate.name());
	}
	
	@Bean
	Binding validationQueueBinding() {
		return BindingBuilder.bind(validationQueue())
				.to(uploadStateExchange())
				.with(UploadState.Validate);
	}

	@Bean
	Queue createQueue() {
		return new Queue(UploadState.Create.name());
	}

	@Bean
	Binding createQueueBinding() {
		return BindingBuilder.bind(createQueue())
				.to(uploadStateExchange())
				.with(UploadState.Create);
	}

	@Bean
	Queue errorQueue() {
		return new Queue(UploadState.Error.name());
	}

	@Bean
	Binding errorQueueBinding() {
		return BindingBuilder.bind(errorQueue())
				.to(uploadStateExchange())
				.with(UploadState.Error);
	}

	@Bean
	Queue successQueue() {
		return new Queue(UploadState.Success.name());
	}

	@Bean
	Binding successQueueBinding() {
		return BindingBuilder.bind(successQueue())
				.to(uploadStateExchange())
				.with(UploadState.Success);
	}
}
