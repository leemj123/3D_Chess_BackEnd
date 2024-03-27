package com.gamza.chess.socket;

import com.gamza.chess.socket.dto.GameRoom;
import com.gamza.chess.socket.dto.SessionPair;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoomManager {
    private final Map<String, GameRoom> gameRoomHash = new HashMap<>();

    public GameRoom getRoomById(String roomId) {

        return gameRoomHash.get(roomId);
    }
    public void addRoom(GameRoom gameRoom) {
        gameRoomHash.put(gameRoom.getRoomId(),gameRoom);
    }
    public int isWhite(WebSocketSession session, SessionPair sessionPair) {
        if (session.getId().equals(sessionPair.getWhite().getId())) {
            return 1;
        } else if (session.getId().equals(sessionPair.getWhite().getId())) {
            return 0;
        }
        return -1;
    }


}
