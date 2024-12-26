package com.jdc.agent.messages;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.jdc.domain.utils.TransactionActivity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashOutInitiationPublisher {
	
	private final RabbitTemplate rabbitTemplate;
	private final TopicExchange agentConsumerCashOutExchange;

	public void publish(String id) {
		rabbitTemplate.convertAndSend(agentConsumerCashOutExchange.getName(), TransactionActivity.Initiate.name(), id);
	}

}
