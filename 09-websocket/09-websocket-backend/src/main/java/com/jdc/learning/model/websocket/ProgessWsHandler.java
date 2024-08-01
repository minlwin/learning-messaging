package com.jdc.learning.model.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jdc.learning.model.listener.ProgressEndEvent;
import com.jdc.learning.model.listener.ProgressEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProgessWsHandler extends TextWebSocketHandler {
	
	private Map<Object, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<>());

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		var historyId = session.getAttributes().get("historyId");
		log.info("Closed : {}", historyId);
		if(null != historyId) {
			sessions.remove(historyId);
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		var historyId = session.getAttributes().get("historyId");
		log.info("Initiate : {}", historyId);
		if(null != historyId) {
			sessions.put(historyId, session);
		}
	}
	
	@EventListener
	void handle(ProgressEvent event) {
		try {
			var session = sessions.get(event.historyId());
			
			if(null != session && session.isOpen()) {
				session.sendMessage(new TextMessage(event.message()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@EventListener
	void handle(ProgressEndEvent event) {
		try {
			log.info("Will Close : {}", event.historyId());
			var session = sessions.get(event.historyId());
			
			if(null != session && session.isOpen()) {
				session.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
