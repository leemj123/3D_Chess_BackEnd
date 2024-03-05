package com.gamza.chess.config.socket;


import com.gamza.chess.jwt.JwtProvider;
import com.gamza.chess.service.gameservice.oldchess.GameMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final JwtProvider jwtProvider;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(), "/game/start")
                .setAllowedOrigins("*")
                .addInterceptors(new SocketHandshakeInterceptor(jwtProvider));
    }
    @Bean
    public WebSocketHandler handler(){
        return new GameSocketHandler();
    }

}
