package com.jdc.agent;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jdc.domain.utils.TransactionActivity;

@Configuration
public class AgentApiAmqpConfig {

	@Value("${app.amqp.wallet.agent.cashout.id}")
	private String agentConsumerCashOutExchangeId;
	
	@Bean
	TopicExchange agentConsumerCashOutExchange() {
		return new TopicExchange(agentConsumerCashOutExchangeId);
	}
	
	@Bean
	Queue confirmationQueue() {
		return new Queue(TransactionActivity.Confirmed.name());
	}

	@Bean
	Binding confirmationQueueBinding() {
		return BindingBuilder.bind(confirmationQueue())
				.to(agentConsumerCashOutExchange())
				.with(TransactionActivity.Confirmed);
	}
	
}
