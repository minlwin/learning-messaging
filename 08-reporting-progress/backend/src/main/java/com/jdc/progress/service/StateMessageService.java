package com.jdc.progress.service;

import java.util.UUID;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateMessageService {
	
	private final RabbitTemplate rabbitTemplate;
	private final TopicExchange uploadStateExchange;
	
	public void send(UUID id, UploadState state) {
		rabbitTemplate.convertAndSend(uploadStateExchange.getName(), state.name(), id.toString());
	}

}
