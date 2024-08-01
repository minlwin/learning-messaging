package com.jdc.progress.service.ws;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdc.progress.utils.dto.ProgressEndEvent;
import com.jdc.progress.utils.dto.ProgressUpdateEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProgressWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<>());

	@EventListener
	public void handle(ProgressUpdateEvent event) {
		try {
			var session = sessions.get(event.historyId());
			
			if(null != session && session.isOpen()) {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(event)));
			}
		} catch (Exception e) {
			log.error("Closing Error for WebSocket Session", e);
		}
	}
	
	@EventListener
	public void handle(ProgressEndEvent event) {
		try {
			var session = sessions.get(event.historyId());
			
			if(null != session && session.isOpen()) {
				session.close();
			}
		} catch (Exception e) {
			log.error("Closing Error for WebSocket Session", e);
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		if(session.getAttributes().get("historyId") instanceof String historyId && StringUtils.hasLength(historyId)) {
			sessions.put(historyId, session);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if(session.getAttributes().get("historyId") instanceof String historyId && StringUtils.hasLength(historyId)) {
			sessions.remove(historyId);
		}
	}
}
