package com.jdc.learning.message.sender;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloWorldSender {

	@Autowired
	private RabbitTemplate template;
	@Autowired
	private Queue queue;
	
	private AtomicInteger count = new AtomicInteger(0);
	
	@Scheduled(fixedRate = 60000)
	public void send() {
		var value = count.addAndGet(1);
		template.convertAndSend(queue.getName(), "Hello World %d".formatted(value));
		log.info("Sending Message %d".formatted(value));
	}
}
