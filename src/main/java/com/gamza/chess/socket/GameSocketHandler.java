package com.gamza.chess.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.socket.messageform.PieceMoveForm;
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
    private final ObjectMapper obj;


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
        JsonNode rootNode;
        ACTION action;
        try {
            rootNode = obj.readTree(message.getPayload());
            action = ACTION.valueOf(rootNode.get("action").asText());

            switch (action) {
                case MOVE:
                    PieceMoveForm pieceMoveForm = obj.treeToValue(rootNode, PieceMoveForm.class);
                    sessionManager.moveRequest(session, pieceMoveForm);
                    break;
                case BOARD_MOVE:
                case SURRENDER:

                case RE_SYNC:

                case PIECE_STATE:
                    sessionManager.getPiecesState(session);
                    break;


            }
        } catch (JsonProcessingException e) {
            sessionManager.textMessageJsonParesError(session);
            return;
        }
    }
}
