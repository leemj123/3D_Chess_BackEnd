package com.gamza.chess.socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.Enum.Color;
import com.gamza.chess.Enum.Tier;
import com.gamza.chess.socket.dto.GameRoom;
import com.gamza.chess.socket.dto.PieceLocation;
import com.gamza.chess.socket.messageform.PieceInitSendForm;
import com.gamza.chess.socket.messageform.RoomInfoForm;
import com.gamza.chess.socket.dto.SessionPair;
import com.gamza.chess.socket.messageform.SyncForm;
import com.gamza.chess.socket.messageform.WinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageProcessor {
    ObjectMapper objectMapper = new ObjectMapper();

    public Mono<Void> estimatedTime(WebSocketSession session, int second) {
        try {
            session.sendMessage(new TextMessage("예상 매칭 소요시간: " + second + "초"));
            return Mono.empty(); // 성공적으로 메시지를 전송한 경우
        } catch (IOException e) {
            return Mono.error(e); // 실패한 경우 에러를 방출
        }}
    public Mono<Void> matchSuccess (SessionPair sessionPair) {
        try {
            sessionPair.getWhite().sendMessage(new TextMessage("게임 시작"));
            sessionPair.getBlack().sendMessage(new TextMessage("게임 시작"));
            return Mono.empty();
        } catch (IOException e) {
            return Mono.error(e); // 실패한 경우 에러를 방출
        }
    }
    public void matchFailed (WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("대기 시간 초과.. 매칭 실패"));
        session.sendMessage(new TextMessage("세션 강제 종료"));

    }
//    public Mono<Void> gameInitInfoSender (SessionPair sessionPair, PieceInitSendForm pieceInitSendForm) {
//        try {
//            String initJson = objectMapper.writeValueAsString(pieceInitSendForm);
//            sessionPair.getWhite().sendMessage(new TextMessage(initJson));
//            sessionPair.getBlack().sendMessage(new TextMessage(initJson));
//            return Mono.empty();
//
//        } catch (IOException e) {
//            return Mono.error(e);
//        }
//    }
public Mono<Void> roomInfoSender(GameRoom gameRoom) {
    try {
        RoomInfoForm forWhite = new RoomInfoForm(gameRoom.getRoomId(), Color.White, gameRoom.getSessionPair());

        RoomInfoForm forBlack = new RoomInfoForm(gameRoom.getRoomId(), Color.Black, gameRoom.getSessionPair());

        gameRoom.getSessionPair().getWhite()
                .sendMessage(new TextMessage(objectMapper.writeValueAsString(forWhite)));

        gameRoom.getSessionPair().getBlack()
                .sendMessage(new TextMessage(objectMapper.writeValueAsString(forBlack)));

        return Mono.empty();
    } catch (IOException e) {
        return Mono.error(e);
    }
}
    public Mono<Void> piecesInfoSender(WebSocketSession session, PieceInitSendForm pieceInitSendForm) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(pieceInitSendForm)));
            return Mono.empty();
        }catch (IOException e) {
            return Mono.error(e);
        }

    }

    public Mono<Void> surrenderWin(WebSocketSession session) {
        try {
            WinForm winForm = WinForm.builder()
                    .action(ACTION.WIN)
                    .message("상대가 도망쳤습니다, 승리!")
                    .beforeTier(Tier.valueOf((String) session.getAttributes().get("tier")))
                    .beforeScore((int) session.getAttributes().get("calScore"))
                    .build();

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(winForm)));
            return Mono.empty();
        } catch (IOException e) {
            return Mono.error(e);
        }
    }
    public Mono<Void> moveResopnse(WebSocketSession session, Integer status) {
        try {
            session.sendMessage(new TextMessage(status.toString()));
            return Mono.empty();
        }  catch (IOException e) {
            return Mono.error(e);
        }
    }
    public Mono<Void> matcherSync(WebSocketSession session, SyncForm syncForm) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(syncForm)));
            return Mono.empty();
        }  catch (IOException e) {
            return Mono.error(e);
        }
    }

    public Mono<Void> jsonParesErrorSender(WebSocketSession session) {
        try {
            session.sendMessage(new TextMessage("JSON 구조 안맞아서나는 에러, 다시 구조 확인 ㄱ"));
            return Mono.empty();
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

}
