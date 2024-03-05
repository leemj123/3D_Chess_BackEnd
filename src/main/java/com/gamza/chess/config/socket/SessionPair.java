package com.gamza.chess.config.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
public class SessionPair {
    private WebSocketSession white;
    private WebSocketSession black;

    public SessionPair(WebSocketSession white, WebSocketSession black) {
        this.white = white;
        this.black = black;
    }
}
