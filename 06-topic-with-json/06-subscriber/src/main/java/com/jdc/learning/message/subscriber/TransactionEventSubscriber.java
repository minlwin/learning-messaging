package com.jdc.learning.message.subscriber;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.jdc.learning.message.dto.TransactionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionEventSubscriber {

	@RabbitListener(queues = "#{transactionQueue.name}")
	public void handle(TransactionDto event) {
		log.info("Received : {}", event.toString());
	}
}
