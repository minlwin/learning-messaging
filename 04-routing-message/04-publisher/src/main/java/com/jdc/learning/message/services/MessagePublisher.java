package com.jdc.learning.message.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jdc.learning.message.Application.Route;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessagePublisher {
	
	@Autowired
	private Exchange exchange;
	@Autowired
	private RabbitTemplate template;
	
	private AtomicInteger dots = new AtomicInteger();
	private AtomicInteger count = new AtomicInteger();

	@Scheduled(fixedRate = 5000)
	public void publish() {
		
		if(dots.incrementAndGet() == 4) {
			dots.set(1);
		}
		
		publish(Route.values()[count.incrementAndGet() % 2]);
	}
	
	
	private void publish(Route route) {
		var message = getMessage(route);
		template.convertAndSend(exchange.getName(), route.name(), message.toString());
		log.info("Send : {}", message.toString());
	}


	private StringBuffer getMessage(Route route) {
		
		var message = new StringBuffer(route.name()).append(" ").append(" ").append(count.get());
		
		if(route == Route.Success) {
			message.append(" ");
			for(var i = 0; i < dots.get(); i ++) {
				message.append(".");
			}
		}
		
		return message;
	}

}
