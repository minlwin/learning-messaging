package com.jdc.learning.message.accounting;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AccountingService {
	
	@Autowired
	private Exchange exchange;
	@Autowired
	private RabbitTemplate template;
	
	private AtomicInteger count = new AtomicInteger();

	@Scheduled(fixedDelay = 2000, initialDelay = 1000)
	public void sendMessage() {
		template.convertAndSend(exchange.getName(), this.getClass().getName(), "Accounting Message %d.".formatted(count.incrementAndGet()));
	}	
}
