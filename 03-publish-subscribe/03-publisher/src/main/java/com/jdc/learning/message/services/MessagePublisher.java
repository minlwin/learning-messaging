package com.jdc.learning.message.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
	
	@Autowired
	private RabbitTemplate rabbit;
	@Autowired
	private FanoutExchange exchange;
	
	private AtomicInteger dots = new AtomicInteger();
	private AtomicInteger count = new AtomicInteger();

	@Scheduled(fixedRate = 10000)
	public void send() {
		
		var message = new StringBuffer("Hello World ");
		
		if(dots.incrementAndGet() == 4) {
			dots.set(1);
		}
		
		for(var i = 0; i < dots.get(); i ++) {
			message.append(".");
		}
		
		message.append(" ").append(count.incrementAndGet());
		
		rabbit.convertAndSend(exchange.getName(), null, message.toString());
		
	}
}
