package com.gamza.chess.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class GameSocketHandler extends TextWebSocketHandler {
    private final SessionManager sessionManager;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        int score = (int) session.getAttributes().get("score");
        //매칭시도 비동기
        sessionManager.sessionMatch(session,score)
                .doOnSuccess(sessionPair -> {
                    if (sessionPair == null)
                        sessionManager.sessionMatchFailed(session);
                    else
                        sessionManager.sessionMatchSuccess(sessionPair);
                }).subscribe();


    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionManager.removeSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {


    }
}
