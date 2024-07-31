package com.jdc.progress.service;

import java.util.UUID;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

@Service
public class StateMessageService {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private TopicExchange uploadStateExchange;
	
	public void send(UUID id, UploadState state) {
		rabbitTemplate.convertAndSend(uploadStateExchange.getName(), state.name(), id.toString());
	}

}
