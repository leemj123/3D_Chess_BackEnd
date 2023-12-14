package com.gamza.chess.config.socket;

import club.gamza.warpsquare.engine.Game;
import club.gamza.warpsquare.engine.Piece;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.Enum.Color;
import com.gamza.chess.dto.SocketPlayerInfoMessage;
import com.gamza.chess.dto.newchessdto.GameInitSendDto;
import com.gamza.chess.dto.newchessdto.PieceLocation;
import com.gamza.chess.dto.newchessdto.PieceMoveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//카페인 알코올 니코틴
@Component
@Slf4j
@RequiredArgsConstructor
public class GameSocketHandler extends TextWebSocketHandler {
    private Deque<WebSocketSession> waitingSessions = new LinkedList<>();
    private Map<WebSocketSession, WebSocketSession> sessionPairs = new ConcurrentHashMap<>();


    //            queue.remove()-> 큐의 첫번째 요소를 삭제(및 반환)한다.     만약 큐가 비어있으면 예외가 발생한다.
    //            queue.poll()-> 큐의 첫번째 요소를 삭제(및 반환)한다.     만약 큐가 비어있으면 null을 반환한다.
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        waitingSessions.add(session);
//
//
//        // 2명의 유저가 매칭 대기열에 있을 경우, 게임 시작 알림 전송
//        if (waitingSessions.size() >= 2) {
//            //세션에서 꺼내와서
//            WebSocketSession player1 = waitingSessions.poll();
//            WebSocketSession player2 = waitingSessions.poll();
//
//            //꺼낸 세션으로 세션id를 redis에 넣는다.
//            String roomKey = String.valueOf(UUID.randomUUID());
//            List<String> playerInfo = gameMainService.playerEstablished(roomKey, player1.getId(),player2.getId());
//
//            //유저 세팅
//            ObjectMapper mapper = new ObjectMapper();
//            SocketPlayerInfoMessage socketPlayer1InfoMessage = SocketPlayerInfoMessage.builder()
//                    .action("COLOR")
//                    .color(playerInfo.get(0))
//                    .build();
//            SocketPlayerInfoMessage socketPlayer2InfoMessage = SocketPlayerInfoMessage.builder()
//                    .action("COLOR")
//                    .color(playerInfo.get(1))
//                    .build();
//
//            String player1info = mapper.writeValueAsString(socketPlayer1InfoMessage);
//            String player2info = mapper.writeValueAsString(socketPlayer2InfoMessage);
//
//            player1.sendMessage(new TextMessage(player1info));
//            player2.sendMessage(new TextMessage(player2info));
//
//            //기물 초기화
//            SocketInitMessageDto socketInitMessageDto = gameMainService.setting2DChessBoard(roomKey);
//            String InitPayload = mapper.writeValueAsString(socketInitMessageDto);
//
//            //메시지 전송
//            player1.sendMessage(new TextMessage(InitPayload));
//            player2.sendMessage(new TextMessage(InitPayload));
//            return;
//        }
//        session.sendMessage(new TextMessage("...매칭 대기 중..."));
//    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("접근 성공" + session.getId());
        if (session.isOpen()) {
            waitingSessions.add(session);
            log.info("add list");
        } else {
            waitingSessions.remove(session);
            log.info("session closed");
        }


        // 2명의 유저가 매칭 대기열에 있을 경우, 게임 시작 알림 전송
        if (waitingSessions.size() >= 2) {
            for (WebSocketSession webSocketSession : waitingSessions) {
                log.info("Session Id: " + webSocketSession.getId());
            }
            //세션에서 꺼내와서
            WebSocketSession player1 = waitingSessions.poll();
            WebSocketSession player2 = waitingSessions.poll();

            if (!player1.isOpen()) {
                waitingSessions.addFirst(player2);
                log.info("player1 error, addFirst to List of player2");
            } else if (!player2.isOpen()) {
                waitingSessions.addFirst(player1);
                log.info("player2 error, addFirst to List of player1");
            } else {
                sessionPairs.put(player1, player2);
                sessionPairs.put(player2, player1);

                try {
                    player1.sendMessage(new TextMessage("Connection Established!"));
                    player2.sendMessage(new TextMessage("Connection Established!"));

                    player1.sendMessage(new TextMessage("You are Player1"));
                    player2.sendMessage(new TextMessage("You are Player2"));
                    ObjectMapper mapper = new ObjectMapper();
                    SocketPlayerInfoMessage socketPlayer1InfoMessage = SocketPlayerInfoMessage.builder()
                            .action(ACTION.COLOR.toString())
                            .color(Color.White.toString())
                            .build();
                    SocketPlayerInfoMessage socketPlayer2InfoMessage = SocketPlayerInfoMessage.builder()
                            .action(ACTION.COLOR.toString())
                            .color(Color.Black.toString())
                            .build();

                    String player1info = mapper.writeValueAsString(socketPlayer1InfoMessage);
                    String player2info = mapper.writeValueAsString(socketPlayer2InfoMessage);

                    player1.sendMessage(new TextMessage(player1info));
                    player2.sendMessage(new TextMessage(player2info));
                    log.info("=====================111======================");
                    Game game = new Game();
                    log.info("=====================222=====================");
                    GameInitSendDto gameInitSendDto = new GameInitSendDto(ACTION.INIT);
                    log.info("=====================333===================");

                    gameInitSendDto.setLocationList(getPieceLocationList(game.getPieces()));
                    log.info("=====================444=====================");


                    String gameInitStatusSend = mapper.writeValueAsString(gameInitSendDto);
                    log.info("=====================555=====================");

                    sendMessageDoublePlayer(player1, player2, gameInitStatusSend);
                } catch (IllegalStateException e) {
                    log.info("IllegalStateException");
                    if (player1.isOpen() || player2.isOpen()) {
                        sessionPairs.remove(player1);
                        sessionPairs.remove(player2);
                        player1.close();
                    }
                }
            }
            //메시지 전송
        }

    }

    private void sendMessageDoublePlayer(WebSocketSession player1, WebSocketSession player2, String message) throws IOException {
        log.info("sendMessage");
        player1.sendMessage(new TextMessage(message));
        player2.sendMessage(new TextMessage(message));
    }

    private List<PieceLocation> getPieceLocationList (Piece[] pieces) {
        log.info("getPieceLocationList");
        return Arrays.stream(pieces)
                .map(piece -> new PieceLocation(piece.getPieceType(),piece.getSquare()))
                .collect(Collectors.toList());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트와의 연결이 종료됐을 때의 처리 로직을 구현합니다.
        WebSocketSession pairedSession = sessionPairs.get(session);

        if (pairedSession != null && pairedSession.isOpen()) {
            try {
                log.info("close start");
                pairedSession.sendMessage(new TextMessage("==================success Close================="));
                log.info("close fin");
            } catch (IllegalStateException e) {
                //일부러 아무처리 안함
            } finally {
                pairedSession.close();
            }
        }

        // 기존 세션과 매핑된 세션 정보 제거
        sessionPairs.remove(session);
        if (pairedSession != null) {
            sessionPairs.remove(pairedSession);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        WebSocketSession pairedSession = sessionPairs.get(session);
        if (pairedSession == null) {
            session.sendMessage(new TextMessage("아직 짝이 이루어지지 않았어요, 상대방이 들어올때까지 기다려주세요"));
            return;
        }

//        ObjectMapper mapper = new ObjectMapper();
//        PieceMoveDto pieceMoveDto = mapper.readValue(message.getPayload(), PieceMoveDto.class);


        session.sendMessage(new TextMessage("you send this: \n"+message.getPayload()));
        pairedSession.sendMessage(new TextMessage("i got this: \n"+message.getPayload()));

    }
}
