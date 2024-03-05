package com.gamza.chess.config.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.dto.newchessdto.GameInitSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class MessageProcessor {
    ObjectMapper objectMapper = new ObjectMapper();

    public void estimatedTime (WebSocketSession session, int second) throws IOException {
        session.sendMessage(new TextMessage("예상 매칭 소요시간: "+second));
    }
    public void matchSuccess (SessionPair sessionPair) throws IOException {
        sessionPair.getWhite().sendMessage(new TextMessage("게임 시작"));
        sessionPair.getBlack().sendMessage(new TextMessage("게임 시작"));
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
