package com.jdc.learning.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.learning.model.entity.ProgressHistory;
import com.jdc.learning.model.io.InitiationForm;
import com.jdc.learning.model.io.InitiationResult;
import com.jdc.learning.model.listener.ProgressInitiationEvent;
import com.jdc.learning.model.repo.ProgressHistoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressHistoryService {
	
	private final ProgressHistoryRepo repo;
	private final ApplicationEventPublisher publisher;

	@Transactional(readOnly = true)
	public List<ProgressHistory> findAll() {
		return repo.findAll();
	}

	@Transactional
	public InitiationResult initiate(InitiationForm form) {
		var entity = new ProgressHistory();
		
		entity.setTitle(form.title());
		entity.setDelayInSec(form.delayInSec());
		entity.setStartAt(LocalDateTime.now());
		
		entity = repo.saveAndFlush(entity);
		
		publisher.publishEvent(new ProgressInitiationEvent(entity.getId()));
		
		return InitiationResult.from(entity);
	}

}
