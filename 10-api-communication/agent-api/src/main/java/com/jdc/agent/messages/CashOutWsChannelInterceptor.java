package com.jdc.agent.messages;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class CashOutWsChannelInterceptor extends HttpSessionHandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		var segments = request.getURI().getPath().split("/");
		attributes.put("accessKey", segments[segments.length - 1]);
		
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
}
