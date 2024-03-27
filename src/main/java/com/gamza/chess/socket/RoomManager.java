package com.gamza.chess.socket;

import com.gamza.chess.socket.dto.GameRoom;
import com.gamza.chess.socket.dto.PieceLocation;
import com.gamza.chess.socket.dto.SessionPair;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomManager {
    private final Map<String, GameRoom> gameRoomHash = new HashMap<>();
    private final Map<String, String> sessionIdToRoomId = new HashMap<>();

    public GameRoom getRoomByRoomId(String roomId) {

        return gameRoomHash.get(roomId);
    }
    public String getRoomIdBySessionId(String sessionId) {
        return sessionIdToRoomId.get(sessionId);
    }
    public GameRoom addRoom(SessionPair sessionPair) {
        GameRoom gameRoom = new GameRoom(sessionPair);
        gameRoomHash.put(gameRoom.getRoomId(),gameRoom);
        sessionIdToRoomId.put(sessionPair.getWhite().getId(), gameRoom.getRoomId());
        sessionIdToRoomId.put(sessionPair.getBlack().getId(), gameRoom.getRoomId());

        return gameRoom;
    }
    public int isWhite(WebSocketSession session, SessionPair sessionPair) {
        if (session.getId().equals(sessionPair.getWhite().getId())) {
            return 1;
        } else if (session.getId().equals(sessionPair.getWhite().getId())) {
            return 0;
        }
        return -1;
    }

    public List<PieceLocation> getPieceList (GameRoom gameRoom) {
        return Arrays.stream(gameRoom.getGame().getPieces())
                .map(piece -> new PieceLocation(piece.getPieceType(),piece.getSquare()))
                .collect(Collectors.toList());
    }

}
