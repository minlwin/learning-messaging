package com.jdc.wallet.messages;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.jdc.domain.utils.TransactionActivity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashOutConfirmationPublisher {

	private final RabbitTemplate rabbitTemplate;
	private final TopicExchange agentConsumerCashOutExchange;

	public void publish(String id) {
		rabbitTemplate.convertAndSend(agentConsumerCashOutExchange.getName(), 
				TransactionActivity.Confirmed.name(), id);
	}
}
