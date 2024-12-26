package com.jdc.agent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.jdc.agent.messages.CashOutWsChannelHandler;
import com.jdc.agent.messages.CashOutWsChannelInterceptor;

@Configuration
@EnableWebSocket
public class AgentApiWebsocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(cashOutWsChannelHandler(), "/ws/consumer/cashout/{accessKey}")
			.addInterceptors(new CashOutWsChannelInterceptor())
			.setAllowedOrigins("*");
	}
	
	@Bean
	CashOutWsChannelHandler cashOutWsChannelHandler() {
		return new CashOutWsChannelHandler();
	}
}
