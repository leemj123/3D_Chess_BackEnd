package com.gamza.chess.piece;

import com.gamza.chess.Enum.Color;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Pawn extends Piece{


    public Pawn(int id, Color color, int x, int y) {
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
        this.live = false;
    }

    @Override
    public void attack(Piece target) {
        target.live();
    }

    public void LeftAttackMove(Piece target) {
        this.x = this.x -1;
        this.y = this.y +1;
    }
    public void RightAttackMove(Piece target) {
        this.x = this.x +1;
        this.y = this.y +1;
    }
}
