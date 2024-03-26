package com.gamza.chess.config.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.dto.newchessdto.GameInitSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Configuration
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
    public void gameInitInfoSender (SessionPair sessionPair, GameInitSendDto gameInitSendDto) throws IOException {
        String initJson = objectMapper.writeValueAsString(gameInitSendDto);

        sessionPair.getWhite().sendMessage(new TextMessage(initJson));
        sessionPair.getBlack().sendMessage(new TextMessage(initJson));
    }
}
