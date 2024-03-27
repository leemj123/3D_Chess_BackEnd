package com.gamza.chess.piece;

import com.gamza.chess.board.Point;
import com.gamza.chess.socket.dto.PieceInfoDto;

import java.util.List;

public class Queen extends Piece{
    public Queen(int id, int x, int y) {
        super(id, x, y);
    }

    public Queen(PieceInfoDto pieceInfoDto) {
        super(pieceInfoDto);
    }

    @Override
    public List<Point> getValidMoveList(List<PieceInfoDto> allInGamePieceList) {
        return null;
    }

    @Override
    public boolean isValidMove(PieceInfoDto pieceInfoDto, List<PieceInfoDto> allInGamePieceList) {
        return false;
    }

    @Override
    public void move() {

    }


}
