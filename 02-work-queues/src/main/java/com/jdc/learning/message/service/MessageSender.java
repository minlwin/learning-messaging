package com.jdc.learning.message.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageSender {

	@Autowired
	private RabbitTemplate template;
	@Autowired
	private Queue queue;
	
	private AtomicInteger dots = new AtomicInteger(0);
	private AtomicInteger count = new AtomicInteger(0);

	@Scheduled(fixedRate = 1500)
	public void send() {
		
		var messageBuilder = new StringBuffer("Hello World ");
		
		if(dots.incrementAndGet() == 4) {
			dots.set(1);
		}
		
		for(var i = 0; i < dots.get(); i ++) {
			messageBuilder.append(". ");
		}
		
		messageBuilder.append(count.incrementAndGet());
		
		log.info("Send {}", messageBuilder.toString());
		
		template.convertAndSend(queue.getName(), messageBuilder.toString());
	}
}
