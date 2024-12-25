package com.jdc.progress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.jdc.progress.model.BaseRepositoryImpl;
import com.jdc.progress.service.ws.ProgressWebSocketHandler;
import com.jdc.progress.service.ws.ProgressWebSocketInterceptor;

@Configuration
@EnableWebSocket
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class BackendMvcConfig implements WebMvcConfigurer, WebSocketConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.allowedHeaders("*");
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(progressWebSocketHandler(), "/progress/{uploadId}")
			.addInterceptors(new ProgressWebSocketInterceptor())
			.setAllowedOrigins("*");
	}
	
	@Bean
	ProgressWebSocketHandler progressWebSocketHandler() {
		return new ProgressWebSocketHandler();
	}
}
