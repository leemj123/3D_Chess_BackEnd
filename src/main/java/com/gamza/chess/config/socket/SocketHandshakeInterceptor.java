package com.gamza.chess.config.socket;

import com.gamza.chess.jwt.JwtProvider;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class SocketHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtProvider jwtProvider;
    public SocketHandshakeInterceptor(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getURI().getQuery().split("token=")[1];
        jwtProvider.validateToken(token);
        Map<String, Object> tokenDecodedInfo =  jwtProvider.getTokenSubject(token);
        attributes.putAll(tokenDecodedInfo);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
