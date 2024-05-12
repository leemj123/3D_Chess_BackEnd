package com.gamza.chess.piece;

import com.gamza.chess.board.Point;
import com.gamza.chess.dto.PieceInfoDto;

import java.util.List;

public class King extends Piece{
    private boolean hasMoved; //캐슬링 체크용

    public King(int id, int x, int y) {
        super(id, x, y);
        this.hasMoved = false;
    }

    public King(PieceInfoDto pieceInfoDto) {
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
