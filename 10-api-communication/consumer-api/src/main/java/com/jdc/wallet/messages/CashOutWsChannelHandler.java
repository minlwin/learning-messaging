package com.jdc.wallet.messages;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jdc.domain.repo.AgentTransactionRepo;

public class CashOutWsChannelHandler extends TextWebSocketHandler{

	@Autowired
	private AgentTransactionRepo agentTransactionRepo;
	
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
	
	@Transactional(readOnly = true)
	@RabbitListener(queues = "#{inititationQueue.name}")
	public void initiated(String message) {
		agentTransactionRepo.findById(message).ifPresent(agentTrx -> {
			var consumerPhone = agentTrx.getConsumer().getPhone();
			
			var session = sessions.get(consumerPhone);
			
			if(null != session) {
				try {
					// Consider about agent transaction id or confirmation information
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
					sessions.remove(consumerPhone);
				}
			}
		});
	}
	
}
