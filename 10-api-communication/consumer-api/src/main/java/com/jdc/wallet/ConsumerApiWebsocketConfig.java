package com.jdc.wallet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.jdc.wallet.ws.CashOutWsChannelHandler;
import com.jdc.wallet.ws.CashOutWsChannelInterceptor;

@Configuration
@EnableWebSocket
public class ConsumerApiWebsocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(cashOutWsChannelHandler(), "/ws/agent/cashout/{accessKey}")
			.addInterceptors(new CashOutWsChannelInterceptor())
			.setAllowedOrigins("*");
	}
	
	@Bean
	CashOutWsChannelHandler cashOutWsChannelHandler() {
		return new CashOutWsChannelHandler();
	}

}
