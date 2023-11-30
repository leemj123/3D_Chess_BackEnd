package com.gamza.chess.config.socket;


import com.gamza.chess.service.gameservice.oldchess.GameMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(), "/game/start")
                .setAllowedOrigins("*");
    }
    @Bean
    public WebSocketHandler handler(){
        return new GameSocketHandler();
    }
}
