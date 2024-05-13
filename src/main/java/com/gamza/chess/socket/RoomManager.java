package com.gamza.chess.socket;

import club.gamza.warpsquare.engine.BoardMove;
import club.gamza.warpsquare.engine.Color;
import club.gamza.warpsquare.engine.PieceMove;
import club.gamza.warpsquare.engine.Square;
import com.gamza.chess.socket.dto.GameRoom;
import com.gamza.chess.socket.dto.PieceLocation;
import com.gamza.chess.socket.dto.SessionPair;
import com.gamza.chess.socket.messageform.PieceMoveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomManager {
    //gameRoom.getGame().delete();
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
        gameRoomHash.put(gameRoom.getRoomId(), gameRoom);
        sessionIdToRoomId.put(sessionPair.getWhite().getId(), gameRoom.getRoomId());
        sessionIdToRoomId.put(sessionPair.getBlack().getId(), gameRoom.getRoomId());

        return gameRoom;
    }

    public boolean isWhite(WebSocketSession session, SessionPair sessionPair) {
        return session.getId().equals(sessionPair.getWhite().getId());

    }

    public List<PieceLocation> getPieceList(GameRoom gameRoom) {
        return Arrays.stream(gameRoom.getGame().getPieces())
                .map(piece -> new PieceLocation(piece.getPieceType(), piece.getSquare()))
                .collect(Collectors.toList());
    }
    public boolean turnChecker (String requestSessionId, GameRoom gameRoom) {
        int colorValue = requestSessionId.equals(gameRoom.getSessionPair().getWhite().getId()) ? 0 : 1;

        return colorValue == gameRoom.getGame().getTurn().getValue();

    }

    //========================
    public int PieceMoveRequest(PieceMoveForm pieceMoveForm, GameRoom gameRoom) {
        Square currentSquare = new Square(pieceMoveForm.getCurrentRank(), pieceMoveForm.getCurrentFile(), pieceMoveForm.getCurrentLevel());
        Square toMoveSquare = new Square(pieceMoveForm.getToMoveRank(), pieceMoveForm.getToMoveFile(), pieceMoveForm.getToMoveLevel());
        PieceMove enginPieceMove = new PieceMove(currentSquare, toMoveSquare, pieceMoveForm.getPieceType());
        int status;
        try {
            if (gameRoom.getGame().legalPieceMove(enginPieceMove)) {

                if (gameRoom.getGame().isCapture(enginPieceMove)) {
                    gameRoom.getGame().getCapturedPieces();
                    log.info("801\n");
                    status = 801;
                }
                else {
                    gameRoom.getGame().pushPieceMove(enginPieceMove);
                    log.info("800\n");
                    status = 800;
                }

                if ( gameRoom.getGame().isCheck() ) {
                    if ( gameRoom.getGame().isCheckmate() ) {
                        return 0; // 게임 종료
                    }
                    status = status == 801 ? 803 : 802;
                }
                return status;
            } else {
                log.info("821\n");
                return 821;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
//    public void AttackBoardMove (GameRoom gameRoom) {
//        gameRoom.getGame().pushBoardMove(BoardMove );
//    }

    private void logger(PieceMoveForm pieceMoveForm) {
        log.info("type: " + pieceMoveForm.getPieceType() + "\n" + "currentLocation: "
                + pieceMoveForm.getCurrentLevel()
                + pieceMoveForm.getCurrentRank()
                + pieceMoveForm.getCurrentFile()
                + "\n\n toMoveLocation"
                + pieceMoveForm.getToMoveLevel()
                + pieceMoveForm.getToMoveRank()
                + pieceMoveForm.getToMoveFile() + "\n\n"
        );
    }

}