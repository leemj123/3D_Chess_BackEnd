package com.gamza.chess.config.socket;

import com.gamza.chess.Enum.Tier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SessionManager {
    private final MessageProcessor messageProcessor;
    /** waitingHashTable
     * 1. 일단 Map이여야함
     * 2. Multi-thread가 able 이여야함
     * > 가능한 종류 {HashTable, ConcurrentHashMap }
     * > 불가능 한 종류 {HashMap -> 내부에 synchronized 비존재}
     * 3. key에 Enum의 socre가 들어감
     *
     * ConcurrentHashMap
     * > Get에 synchronized 없음
     * > Put, delete -> 동기화처리 ㄱㄴ
     */
    private final ConcurrentHashMap<Integer, List<WebSocketSession>> waitingHashTable = new ConcurrentHashMap<>();
    private Map<WebSocketSession, WebSocketSession> sessionPairsHashTable = new ConcurrentHashMap<>();

    //동시성 컨트롤 문제

    /**
     * 함수에 synchronized 걸면 부하 우려
     * 멀티 스레드에서 공유되는 주체인 waitingHashTable을 집중 관리
     */
    public int estimateWaitTime(int score, Tier tier) {
        int total = 0;
        for (int i = score -2; i <= score+2; i++) {
            total += waitingHashTable.get(i).size();
        }
    }
    public SessionPair sessionMatch(WebSocketSession session, int score, Tier tier) {

        WebSocketSession matchTargetSession = circuitTableRow(score);
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

        //매칭 실패했을때 처리
        //대기열에 집어넣고 지속 대기 상태
        synchronized (waitingHashTable) {
            waitingHashTable.get(score).add(session);
        }

        return null;
    }


    public WebSocketSession circuitTableRow (int score) {
        List<WebSocketSession> sessionList = waitingHashTable.putIfAbsent(score,Collections.synchronizedList(new ArrayList<>()));
        if (sessionList.isEmpty() || sessionList == null )
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
    public SessionPair assignRandomColor(WebSocketSession session, WebSocketSession matchTargetSession) {
        int key = Math.random() < 0.5 ? 0 : 1;
        return key == 1 ? new SessionPair(session, matchTargetSession) : new SessionPair(matchTargetSession, session);
    }
//    public void addSession (WebSocketSession session) {
//        log.info("접근 성공" + session.getId());
//        if (session.isOpen()) {
//            waitingHashTable.add(session);
//            log.info("add list");
//        } else {
//            log.info("session closed");
//        }
//
//    }

    public void sessionMatchFailed (WebSocketSession session) throws IOException {
        messageProcessor.matchFailed(session);
        session.close();
    }
    public void sessionMatchSuccess (SessionPair sessionPair) throws IOException {
        messageProcessor.matchSuccess(sessionPair);
        sessionPairsHashTable.put(sessionPair.getBlack(), sessionPair.getWhite());
    }

    public void removeSession(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트와의 연결이 종료됐을 때의 처리 로직을 구현합니다.
        WebSocketSession pairedSession = sessionPairsHashTable.get(session);


        // 기존 세션과 매핑된 세션 정보 제거
        sessionPairsHashTable.remove(session);
        if (pairedSession != null) {
            sessionPairsHashTable.remove(pairedSession);
        }
    }
}
