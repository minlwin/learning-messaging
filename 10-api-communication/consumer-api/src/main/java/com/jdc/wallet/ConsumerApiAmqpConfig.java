package com.jdc.wallet;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jdc.domain.utils.TransactionActivity;

@Configuration
public class ConsumerApiAmqpConfig {

	@Value("${app.amqp.wallet.agent.cashout.id}")
	private String agentConsumerCashOutExchangeId;
	
	@Bean
	TopicExchange agentConsumerCashOutExchange() {
		return new TopicExchange(agentConsumerCashOutExchangeId);
	}
	
	@Bean
	Queue inititationQueue() {
		return new Queue(TransactionActivity.Initiate.name());
	}

	@Bean
	Binding initiationQueueBinding() {
		return BindingBuilder.bind(inititationQueue())
				.to(agentConsumerCashOutExchange())
				.with(TransactionActivity.Initiate);
	}
}
