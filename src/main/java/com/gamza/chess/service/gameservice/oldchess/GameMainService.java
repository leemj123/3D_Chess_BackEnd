package com.gamza.chess.service.gameservice.oldchess;

import com.gamza.chess.board.Point;
import com.gamza.chess.board.TwoDBoard;
import com.gamza.chess.dto.SocketInitMessageDto;
import com.gamza.chess.dto.PieceInfoDto;
import com.gamza.chess.piece.*;
import com.gamza.chess.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameMainService {
    private final ChessBoardSetting chessBoardSetting;
    private final RedisService redisService;

    public List<String> playerEstablished(String roomKey, String playerId1, String playerId2) {
        //유저 색 정하기
        List<String> playerInfo = new ArrayList<>();
        if (new Random().nextBoolean()){
            redisService.establishedGame(roomKey.toString(),playerId1,playerId2);
            playerInfo.add("white");
            playerInfo.add("black");
        } else {
            redisService.establishedGame(roomKey.toString(),playerId2,playerId1);
            playerInfo.add("black");
            playerInfo.add("white");
        }
        return playerInfo;
    }

    //======================================================
    public SocketInitMessageDto setting2DChessBoard(String roomKey) {

        TwoDBoard twoDBoard = new TwoDBoard();
        twoDBoard.inItBoard(chessBoardSetting.setting(twoDBoard.MAX_X, twoDBoard.MAX_Y));
        SocketInitMessageDto socketInitMessageDto = SocketInitMessageDto.builder()
                .action("INIT")
                .initPieces(
                        twoDBoard.getBoard().stream().map(piece -> {
                            PieceInfoDto dto = new PieceInfoDto(piece);
                            redisService.savePiece(dto,roomKey);
                            return dto;
                        }).collect(Collectors.toList()))
                .build();

        return socketInitMessageDto;
    }
    public PieceInfoDto movePiece(String roomKey,PieceInfoDto pieceInfoDto) {
        Piece piece = bringPieceInfo(roomKey, pieceInfoDto.getId());
        if (!piece.isValidMove(pieceInfoDto,redisService.getAllInGamePieceList(roomKey))) {
            throw new RuntimeException();
        }
        return redisService.updatePiece(roomKey, pieceInfoDto);
    }
    public List<Point> getMoveValidList(String roomId,int id) {
        Piece piece = bringPieceInfo(roomId, id);
        return piece.getValidMoveList(redisService.getAllInGamePieceList(roomId));
    }


    private Piece bringPieceInfo(String roomKey,int pieceId){

        if (9<= pieceId && pieceId <= 24) {
            return new Pawn(redisService.checkPieceLocation(roomKey, pieceId));
        }
        switch (pieceId) {
            case 1: case 8: case 25: case 32:
                return new Rook(redisService.checkPieceLocation(roomKey, pieceId));
            case 2: case 7: case 26: case 31:
                return new Knight(redisService.checkPieceLocation(roomKey, pieceId));
            case 3: case 6: case 27: case 30:
                return new Bishop(redisService.checkPieceLocation(roomKey, pieceId));
            case 4: case 28:
                return new Queen(redisService.checkPieceLocation(roomKey, pieceId));
            case 5: case 29:
                return new King(redisService.checkPieceLocation(roomKey, pieceId));
            default:
                throw new RuntimeException();

        }

    }
}
