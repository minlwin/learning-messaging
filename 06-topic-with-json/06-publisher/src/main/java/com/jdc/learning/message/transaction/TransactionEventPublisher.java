package com.jdc.learning.message.transaction;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jdc.learning.message.dto.TransactionDto;
import com.jdc.learning.message.dto.TransactionDto.Action;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionEventPublisher {
	
	@Autowired
	private TopicExchange exchange;
	
	@Autowired
	private RabbitTemplate template;

	private AtomicInteger counter = new AtomicInteger();
	private AtomicInteger index = new AtomicInteger();
	
	@Scheduled(initialDelay = 5000, fixedDelay = 3000)
	public void send() {
		
		var message = new TransactionDto(counter.incrementAndGet(), Action.values()[index.get()], LocalDateTime.now());
		
		if(index.incrementAndGet() == 3) {
			index.set(0);
		}
		
		template.convertAndSend(exchange.getName(), getClass().getName(), message);
		log.info("Send : {}", message);
	}
}
