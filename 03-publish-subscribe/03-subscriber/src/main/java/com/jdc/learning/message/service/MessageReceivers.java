package com.jdc.learning.message.service;

import java.time.Duration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageReceivers {

	@RabbitListener(queues = "#{autoDeleteQueue1.name}")
	public void receive1(String message) {
		receive(1, message);
	}
	
	@RabbitListener(queues = "#{autoDeleteQueue1.name}")
	public void receive2(String message) {
		receive(2, message);
	}

	private void receive(int receiver, String message) {
		log.info("Receiver {} receive {}", receiver, message);
		
		var watch = new StopWatch();
		watch.start();
		
		doWork(receiver, message);
		
		watch.stop();
		
		log.info("Receiver {} has done after {} ms", receiver, watch.getTotalTimeMillis());	
	}
	
	private void doWork(int receiver, String message) {
		
		for(var c : message.toCharArray()) {
			if(c == '.') {
				try {
					Thread.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException e) {
					log.error("Receiver {} Thread had been interrupted.", receiver);
				}
			}
		}
		
	}
	
}
