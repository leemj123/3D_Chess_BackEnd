package com.gamza.chess.piece;

import java.util.List;
import java.util.Map;

public class Queen extends Piece{
    public Queen(int id, int x, int y) {
        super(id, x, y);
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

    }


}
