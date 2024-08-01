package com.jdc.learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.jdc.learning.model.websocket.ProgessWsHandler;
import com.jdc.learning.model.websocket.ProgressWsInterceptor;

@EnableAsync
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer{

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
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*");
	}
	
}
