package com.jdc.learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.jdc.learning.model.websocket.ProgessWsHandler;
import com.jdc.learning.model.websocket.ProgressWsInterceptor;

@EnableAsync
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(progessWsHandler(), "/ws/{historyId}")
			.addInterceptors(new ProgressWsInterceptor())
			.setAllowedOrigins("*");
	}
	
	@Bean
	ProgessWsHandler progessWsHandler() {
		return new ProgessWsHandler();
	}
	
}
