package com.gamza.chess.config.socket;

import club.gamza.warpsquare.engine.Game;
import com.gamza.chess.config.socket.dto.SessionPair;
import com.gamza.chess.config.socket.dto.GameInitSendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SessionManager {
    private final MessageProcessor messageProcessor;
    private final GameLogicService gameLogicService;
    private final ConcurrentHashMap<Integer, List<WebSocketSession>> waitingHashTable = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, WebSocketSession> sessionPairsHashTable = new HashMap<>();
    private final static Map<String, Game> gameStatus = new HashMap<>();

    //동시성 컨트롤 문제

    //aging 처리가 약간 가라느낌?

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
    public void removeSession(WebSocketSession session) {
        // 클라이언트와의 연결이 종료됐을 때의 처리 로직을 구현합니다.
        WebSocketSession pairedSession = sessionPairsHashTable.get(session);


        // 기존 세션과 매핑된 세션 정보 제거
        sessionPairsHashTable.remove(session);
        if (pairedSession != null) {
            sessionPairsHashTable.remove(pairedSession);
        }
    }
    private SessionPair assignRandomColor(WebSocketSession session, WebSocketSession matchTargetSession) {
        int key = Math.random() < 0.5 ? 0 : 1;
        return key == 1 ? new SessionPair(session, matchTargetSession) : new SessionPair(matchTargetSession, session);
    }


    //상대 정보 넘겨줘야하고
    //
    public void sessionMatchSuccess (SessionPair sessionPair) {
        messageProcessor.matchSuccess(sessionPair)
                .doOnError(Throwable::printStackTrace)
                .subscribe();

        sessionPairsHashTable.put(sessionPair.getBlack(), sessionPair.getWhite());
        sessionPairsHashTable.put(sessionPair.getWhite(), sessionPair.getBlack());

        //유저 칼라 전송
        messageProcessor.userColorSender(sessionPair)
                .doOnError(e -> log.error("userColorSender error \n"+e))
                .subscribe();
        //상대유저 정보 전송
        messageProcessor.matchedUserInfoSender(sessionPair)
                .doOnError(e -> log.error("userInfoSender error \n"+e))
                .subscribe();

        Game game = new Game();
        gameStatus.put(sessionPair.getBlack().getId(), game);
        gameStatus.put(sessionPair.getWhite().getId(), game);

        GameInitSendDto gameInitSendDto = new GameInitSendDto(gameLogicService.getPieceLocationList(game.getPieces()));


        messageProcessor.gameInitInfoSender(sessionPair, gameInitSendDto)
                .doOnError(e -> log.error("gameInitInfoSender error \n"+e))
                .subscribe();

    }



}
