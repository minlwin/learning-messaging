package com.jdc.learning.message.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccountingSubscriber {

	@RabbitListener(queues = "#{accountingQueue.name}")
	public void handle(String message) {
		log.info("Accounting : {}", message);
	}
}
