package com.jdc.learning.model.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class ProgressWsInterceptor extends HttpSessionHandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		var pathInfo = request.getURI().getPath().split("/");
		
		if(pathInfo.length >= 3) {
			attributes.put("historyId", pathInfo[2]);
		}
		
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
}
