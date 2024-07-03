package com.jdc.learning.message.subscribers;

import java.time.Duration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuccessMessageSubscribers {

	@RabbitListener(queues = "#{successQueue1.name}")
	public void subscribe1(String message) {
		handle(1, message);
	}


	@RabbitListener(queues = "#{successQueue2.name}")
	public void subscribe2(String message) {
		handle(2, message);
	}
	
	private void handle(int receiver, String message) {
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
