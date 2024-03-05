package com.gamza.chess.config.socket;

import club.gamza.warpsquare.engine.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.Enum.Color;
import com.gamza.chess.dto.SocketPlayerInfoMessage;
import com.gamza.chess.dto.newchessdto.GameInitSendDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Configuration
public class MatchManager {

    public void tryMatch () {

    }


//    public void oldMatch() {
//        // 2명의 유저가 매칭 대기열에 있을 경우, 게임 시작 알림 전송
//        if (waitingSessions.size() >= 2) {
//            for (WebSocketSession webSocketSession : waitingSessions) {
//                log.info("Session Id: " + webSocketSession.getId());
//            }
//            //세션에서 꺼내와서
//            gameLogicService.gameInit();
//            WebSocketSession player1 = waitingSessions.poll();
//            WebSocketSession player2 = waitingSessions.poll();
//
//            if (!player1.isOpen()) {
//                waitingSessions.addFirst(player2);
//                log.info("player1 error, addFirst to List of player2");
//            } else if (!player2.isOpen()) {
//                waitingSessions.addFirst(player1);
//                log.info("player2 error, addFirst to List of player1");
//            } else {
//                sessionPairs.put(player1, player2);
//                sessionPairs.put(player2, player1);
//
//                try {
//                    player1.sendMessage(new TextMessage("Connection Established!"));
//                    player2.sendMessage(new TextMessage("Connection Established!"));
//
//                    player1.sendMessage(new TextMessage("You are Player1"));
//                    player2.sendMessage(new TextMessage("You are Player2"));
//                    ObjectMapper mapper = new ObjectMapper();
//                    SocketPlayerInfoMessage socketPlayer1InfoMessage = SocketPlayerInfoMessage.builder()
//                            .action(ACTION.COLOR.toString())
//                            .color(Color.White.toString())
//                            .build();
//                    SocketPlayerInfoMessage socketPlayer2InfoMessage = SocketPlayerInfoMessage.builder()
//                            .action(ACTION.COLOR.toString())
//                            .color(Color.Black.toString())
//                            .build();
//
//                    String player1info = mapper.writeValueAsString(socketPlayer1InfoMessage);
//                    String player2info = mapper.writeValueAsString(socketPlayer2InfoMessage);
//
//                    player1.sendMessage(new TextMessage(player1info));
//                    player2.sendMessage(new TextMessage(player2info));
//                    log.info("=====================111======================");
//                    Game game = new Game();
//                    log.info("=====================222=====================");
//                    GameInitSendDto gameInitSendDto = new GameInitSendDto(ACTION.INIT);
//                    log.info("=====================333===================");
//
//                    gameInitSendDto.setLocationList(getPieceLocationList(game.getPieces()));
//                    log.info("=====================444=====================");
//
//
//                    String gameInitStatusSend = mapper.writeValueAsString(gameInitSendDto);
//                    log.info("=====================555=====================");
//
//                    sendMessageDoublePlayer(player1, player2, gameInitStatusSend);
//                } catch (IllegalStateException e) {
//                    log.info("IllegalStateException");
//                    if (player1.isOpen() || player2.isOpen()) {
//                        sessionPairs.remove(player1);
//                        sessionPairs.remove(player2);
//                        player1.close();
//                    }
//                }
//            }
//            //메시지 전송
//        }
//
//    }
//    }
}
