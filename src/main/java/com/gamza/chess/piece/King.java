package com.gamza.chess.piece;

import com.gamza.chess.Enum.Color;

import java.util.List;
import java.util.Map;

public class King extends Piece{
    private boolean hasMoved = false; //캐슬링 체크용

    public King(int id, Color color, int x, int y) {
        super(id, color, x, y);
    }

    @Override
    public List<Map<Integer, Integer>> validMoveList() {
        return null;
    }

    @Override
    public boolean isValidMove() {
        return false;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public void live() {

    }

    @Override
    public void attack(Piece target) {

    }
}
