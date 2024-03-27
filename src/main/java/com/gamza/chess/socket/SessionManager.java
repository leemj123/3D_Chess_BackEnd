package com.gamza.chess.socket;

import com.gamza.chess.socket.dto.GameRoom;
import com.gamza.chess.socket.dto.SessionPair;
import com.gamza.chess.socket.messageform.PieceInitSendForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionManager {
    private final MessageProcessor messageProcessor;
    private final RoomManager roomManager;
    private final ConcurrentHashMap<Integer, List<WebSocketSession>> waitingHashTable = new ConcurrentHashMap<>();


    public Mono<SessionPair> sessionMatch(WebSocketSession session, int score) {
        return Mono.create(sink -> {
            // 매칭 시도
            log.info("sessionMatch 32");
            SessionPair sessionPair = this.tryToMatch(session, score);
            if (sessionPair != null) {
                log.info("Match found for session ID: {}", session.getId());
                sink.success(sessionPair);
                return;
            }
            addToWaitingQueue(session, score);
            // 점수대에 맞는 대기중인 유저를 찾을 수 없음
            // 대기열에 추가하고 20초 후 매칭 실패 처리
            messageProcessor.estimatedTime(session, 30)
                    .doOnError(e -> log.error("Error sending estimated time message", e))
                    .then(Mono.delay(Duration.ofSeconds(30)))
                    .doOnSuccess(done -> {
                        if (isSessionStillWaiting(session,score)){
                            log.info("Waiting period over for session ID: {}", session.getId());
                            removeFromWaitingQueue(session,score);
                            sink.success(null);
                        }
                    })
                    .subscribe();

        });
    }
    private boolean isSessionStillWaiting (WebSocketSession session, int score) {

        List<WebSocketSession> sessions = waitingHashTable.get(score);
        if (sessions != null) {
            return sessions.contains(session);
        }
        return false;

    }
    private void removeFromWaitingQueue(WebSocketSession session, int score) {

        List<WebSocketSession> sessions = waitingHashTable.get(score);
        sessions.remove(session);

    }
    public void sessionMatchFailed (WebSocketSession session){
        try {
            messageProcessor.matchFailed(session);
            session.close();
        } catch (IOException e) {
            throw Exceptions.propagate(e); // 리액티브 스트림의 에러 채널로 예외 전파
        }
    }
    private void addToWaitingQueue(WebSocketSession session, int score) {
        //매칭 실패했을때 처리
        //대기열에 집어넣고 지속 대기 상태
        waitingHashTable.get(score).add(session);
    }

    private SessionPair tryToMatch(WebSocketSession session, int score) {
        WebSocketSession matchTargetSession = circuitTableRow(score);
        log.info("tryToMatch 68");
        if ( matchTargetSession != null )
            return assignRandomColor(session, matchTargetSession);

        int step = 1;

        while (step < 3) {
            if ( (matchTargetSession = circuitTableRow(score + step)) != null ) {
                return assignRandomColor(session, matchTargetSession);
            }

            if ( (matchTargetSession = circuitTableRow(score - step)) != null ) {
                return assignRandomColor(session, matchTargetSession);
            }

            step++;
        }

        return null;
    }



    public WebSocketSession circuitTableRow (int score) {
        if (score < 0)
            return null;

        List<WebSocketSession> sessionList = waitingHashTable.putIfAbsent(score,Collections.synchronizedList(new ArrayList<>()));
        if (sessionList == null )
            return null;

        while (!sessionList.isEmpty()) {

            synchronized (sessionList) {
                WebSocketSession matchTargetSession = sessionList.get(0);
                sessionList.remove(0);
                if ( matchTargetSession.isOpen() )
                    return matchTargetSession;
            }

        }
        return null;
    }

    private SessionPair assignRandomColor(WebSocketSession session, WebSocketSession matchTargetSession) {
        int key = Math.random() < 0.5 ? 0 : 1;
        return key == 1 ? new SessionPair(session, matchTargetSession) : new SessionPair(matchTargetSession, session);
    }

    public void removeSession(WebSocketSession disconnectedSession) {
        String roomId = roomManager.getRoomIdBySessionId(disconnectedSession.getId());
        if (roomId == null)
            return;

        GameRoom gameRoom = roomManager.getRoomByRoomId(roomId);
        //게임룸이 없어지지 않았을 경우에 승리 처리
        if ( gameRoom != null ) {
            switch (roomManager.isWhite(disconnectedSession, gameRoom.getSessionPair())) {
                case 1: {
                    messageProcessor.surrenderWin( gameRoom.getSessionPair().getBlack() )
                            .doOnError(e -> log.error("surrenderWin error \n" +e))
                            .subscribe();
                    /*
                        결과 저장 로직 추가해야함
                     */

                }
                case 0: {
                    messageProcessor.surrenderWin( gameRoom.getSessionPair().getWhite() )
                            .doOnError(e -> log.error("surrenderWin error \n" +e))
                            .subscribe();
                }
                default:
            }
        }

    }

    public void sessionMatchSuccess (SessionPair sessionPair) {
        messageProcessor.matchSuccess(sessionPair)
                .doOnError(Throwable::printStackTrace)
                .subscribe();


        GameRoom gameRoom =  roomManager.addRoom(sessionPair);

        messageProcessor.roomInfoSender(gameRoom)
                .doOnError(e -> log.error("roomInfoSender error \n"+e))
                .subscribe();

        PieceInitSendForm pieceInitSendForm = new PieceInitSendForm(roomManager.getPieceList(gameRoom));
        messageProcessor.gameInitInfoSender(sessionPair, pieceInitSendForm)
                .doOnError(e -> log.error("gameInitInfoSender error \n"+e))
                .subscribe();

    }

}
