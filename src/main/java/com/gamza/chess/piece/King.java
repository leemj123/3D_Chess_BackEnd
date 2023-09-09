package com.gamza.chess.piece;

import com.gamza.chess.Enum.Color;

import java.util.List;
import java.util.Map;

public class King extends Piece{
    private boolean hasMoved; //캐슬링 체크용

    public King(int id, int x, int y) {
        super(id, x, y);
        this.hasMoved = false;
    }

    @Override
    public List<Map<Integer, Integer>> getValidMoveList() {
        return null;
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public void move(int x, int y) {
        this.hasMoved = true;
    }


}
