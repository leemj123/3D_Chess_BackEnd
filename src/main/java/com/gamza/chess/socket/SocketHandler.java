package com.gamza.chess.socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class SocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws IOException {
        String receivedMessage = message.getPayload();
        socketSession.sendMessage(new TextMessage("Received: "+receivedMessage));
    }
}
