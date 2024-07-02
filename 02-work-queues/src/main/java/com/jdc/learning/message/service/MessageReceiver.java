package com.jdc.learning.message.service;

import java.time.Duration;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RabbitListener(queues = "hello")
public class MessageReceiver {

	private final int instance;

	public MessageReceiver(int instance) {
		super();
		this.instance = instance;
	}
	
	@RabbitHandler
	public void handle(String message) {
		
		log.info("Receiver {} receive {}", instance, message);
		
		var watch = new StopWatch();
		watch.start();
		
		doWork(message);
		
		watch.stop();
		
		log.info("Receiver {} has done after {} ms", instance, watch.getTotalTimeMillis());
	}

	private void doWork(String message) {
		
		for(var c : message.toCharArray()) {
			if(c == '.') {
				try {
					Thread.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException e) {
					log.error("Receiver {} Thread had been interrupted.", instance);
				}
			}
		}
		
	}
}
