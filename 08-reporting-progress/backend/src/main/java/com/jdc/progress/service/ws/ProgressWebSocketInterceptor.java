package com.jdc.progress.service.ws;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class ProgressWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		super.beforeHandshake(request, response, wsHandler, attributes);
		
		var paths = request.getURI().getPath().split("/");
		
		if(paths.length >= 3) {
			attributes.put("historyId", paths[2]);
		}
		
		return true;
	}
}
