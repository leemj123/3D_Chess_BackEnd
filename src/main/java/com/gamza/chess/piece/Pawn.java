package com.gamza.chess.piece;

import com.gamza.chess.Enum.Color;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(int id, int x, int y) {
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
        this.x = x;
        this.y = y;
        this.hasMoved = true;
    }

}
