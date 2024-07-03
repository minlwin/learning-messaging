package com.jdc.learning.message.subscribers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ErrorMessageSubscriber {

	@RabbitListener(queues = "#{errorQueue.name}")
	public void subscribe(String message) {
		log.error(message);
	}
}
