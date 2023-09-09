package com.gamza.chess.piece;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamza.chess.Enum.Color;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public abstract class Piece {

    private int id;
    int x;
    int y;

    public Piece(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public abstract List<Map<Integer, Integer>> getValidMoveList();
    public abstract boolean isValidMove();
    public abstract void move(int x, int y);
}
