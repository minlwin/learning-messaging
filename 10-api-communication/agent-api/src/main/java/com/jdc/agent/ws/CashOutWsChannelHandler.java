package com.jdc.agent.ws;

import java.io.IOException;
import java.time.LocalDateTime;
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
import com.jdc.domain.utils.TransactionStatus;

public class CashOutWsChannelHandler extends TextWebSocketHandler{

	@Autowired
	private AgentTransactionRepo transactionRepo;
	
	private Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		var accessKey = session.getAttributes().get("accessKey");
		
		if(accessKey instanceof String key) {
			sessions.put(key, session);
		}
	}

	@Override
	@Transactional
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		var accessKey = session.getAttributes().get("accessKey");
		
		if(accessKey instanceof String key) {
			sessions.remove(key);
			
			transactionRepo.findById(key).ifPresent(transaction -> {
				if(transaction.getStatus() == TransactionStatus.Initiate) {
					transaction.setStatus(TransactionStatus.Timeout);
					transaction.setFinishedAt(LocalDateTime.now());
				}
			});
		}
	}
	
	@Transactional(readOnly = true)
	@RabbitListener(queues = "#{confirmationQueue.name}")
	public void confirmed(String message) {
		transactionRepo.findById(message).ifPresent(agentTrx -> {
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
