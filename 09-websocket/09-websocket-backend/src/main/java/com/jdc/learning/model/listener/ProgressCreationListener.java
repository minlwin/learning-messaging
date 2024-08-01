package com.jdc.learning.model.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jdc.learning.model.repo.ProgressHistoryRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressCreationListener {
	
	private final ProgressHistoryRepo repo;
	private final ApplicationEventPublisher publisher;

	@Async
	@EventListener
	public void handle(ProgressInitiationEvent event) {
		
		log.info("Accept Event {}", event.historyId());
		
		try {
			var history = repo.findById(event.historyId()).orElseThrow();
			log.info("Progress info is {}", history);
			
			var step = 0;
			
			while(step < 10) {
				Thread.sleep(history.getDelayInSec() * 500);

				log.info("Step is {} / 10", ++ step);
				
				publisher.publishEvent(new ProgressEvent(history.getId().toString(), "Progress is %d / 10.".formatted(step)));
			}
			
			log.info("End Event {}", event.historyId());
			
			publisher.publishEvent(new ProgressEndEvent(history.getId().toString()));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
