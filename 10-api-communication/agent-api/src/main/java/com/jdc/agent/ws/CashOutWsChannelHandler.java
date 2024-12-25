package com.jdc.agent.ws;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class CashOutWsChannelHandler extends TextWebSocketHandler{

	private Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		var accessKey = session.getAttributes().get("accessKey");
		
		if(accessKey instanceof String key) {
			sessions.put(key, session);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		var accessKey = session.getAttributes().get("accessKey");
		
		if(accessKey instanceof String key) {
			sessions.remove(key);
		}
	}
	
}
