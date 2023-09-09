package com.gamza.chess.service.chessService;

import com.gamza.chess.board.TwoDBoard;
import com.gamza.chess.dto.PieceInfoDto;
import com.gamza.chess.piece.Piece;
import com.gamza.chess.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameMainService {
    private final ChessBoardSetting chessBoardSetting;
    private final RedisService redisService;

    public PieceInfoDto[][] setting2DChessBoard(String roomId) {

        TwoDBoard twoDBoard = new TwoDBoard();
        twoDBoard.inItBoard(chessBoardSetting.setting(twoDBoard.MAX_X, twoDBoard.MAX_Y));

        return Arrays.stream(twoDBoard.getBoard())
                .map(row -> Arrays.stream(row)
                        .map(piece -> {
                            if ( piece == null) return null;
                            PieceInfoDto dto = new PieceInfoDto(piece);
                            redisService.savePiece(dto,roomId);
                            return dto;
                        })
                        .toArray(PieceInfoDto[]::new))
                .toArray(PieceInfoDto[][]::new);
    }
    public PieceInfoDto movePieceOfRoomId(String roomId,PieceInfoDto pieceInfoDto){

        return redisService.updatePiece(roomId, pieceInfoDto);
    }
}
