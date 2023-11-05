package com.gamza.chess.config.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.dto.SocketInitMessageDto;
import com.gamza.chess.dto.SocketPlayerInfoMessage;
import com.gamza.chess.service.gameservice.GameMainService;
import com.gamza.chess.service.gameservice.SocketJsonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
//카페인 알코올 니코틴
@Component
@RequiredArgsConstructor
public class GameSocketHandler extends TextWebSocketHandler {
    private Queue<WebSocketSession> waitingSessions = new ConcurrentLinkedQueue<>();
    private final GameMainService gameMainService;

    //            queue.remove()-> 큐의 첫번째 요소를 삭제(및 반환)한다.     만약 큐가 비어있으면 예외가 발생한다.
    //            queue.poll()-> 큐의 첫번째 요소를 삭제(및 반환)한다.     만약 큐가 비어있으면 null을 반환한다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        waitingSessions.add(session);


        // 2명의 유저가 매칭 대기열에 있을 경우, 게임 시작 알림 전송
        if (waitingSessions.size() >= 2) {
            //세션에서 꺼내와서
            WebSocketSession player1 = waitingSessions.poll();
            WebSocketSession player2 = waitingSessions.poll();

            //꺼낸 세션으로 세션id를 redis에 넣는다.
            String roomKey = String.valueOf(UUID.randomUUID());
            List<String> playerInfo = gameMainService.playerEstablished(roomKey, player1.getId(),player2.getId());

            //유저 세팅
            ObjectMapper mapper = new ObjectMapper();
            SocketPlayerInfoMessage socketPlayer1InfoMessage = SocketPlayerInfoMessage.builder()
                    .action("COLOR")
                    .color(playerInfo.get(0))
                    .build();
            SocketPlayerInfoMessage socketPlayer2InfoMessage = SocketPlayerInfoMessage.builder()
                    .action("COLOR")
                    .color(playerInfo.get(1))
                    .build();

            String player1info = mapper.writeValueAsString(socketPlayer1InfoMessage);
            String player2info = mapper.writeValueAsString(socketPlayer2InfoMessage);

            player1.sendMessage(new TextMessage(player1info));
            player2.sendMessage(new TextMessage(player2info));

            //기물 초기화
            SocketInitMessageDto socketInitMessageDto = gameMainService.setting2DChessBoard(roomKey);
            String InitPayload = mapper.writeValueAsString(socketInitMessageDto);

            //메시지 전송
            player1.sendMessage(new TextMessage(InitPayload));
            player2.sendMessage(new TextMessage(InitPayload));
            return;
        }
        session.sendMessage(new TextMessage("...매칭 대기 중..."));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SocketJsonDto socketJsonDto = new ObjectMapper().readValue(message.getPayload(), SocketJsonDto.class);
        switch (socketJsonDto.getAction()) {
            case "MOVE":
                //gameMainService.movePiece();
                break;
            case "MOVE_VALID":
                gameMainService.getMoveValidList(socketJsonDto.getRoomKey(), socketJsonDto.getPieceId());
                break;


        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO: 클라이언트와의 연결이 종료됐을 때의 처리 로직을 구현합니다.
    }
}
//    void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
//    한쪽에서 WebSocket 연결을 닫은 후 또는 전송 오류가 발생한 후에 호출됩니다.
//      새로운 클라이언트 연결 시 처리 로직을 구현합니다.

//        void afterConnectionEstablished(WebSocketSession session)
//        WebSocket 협상이 성공하고 WebSocket 연결이 열리고 사용할 준비가 된 후에 호출됩니다.

//        void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
//        새 WebSocket 메시지가 도착하면 호출됩니다.

//        void handleTransportError(WebSocketSession session, Throwable exception)
//        기본 WebSocket 메시지 전송의 오류를 처리합니다.

//        boolean supportsPartialMessages()
//        WebSocketHandler가 부분 메시지를 처리하는지 여부입니다.