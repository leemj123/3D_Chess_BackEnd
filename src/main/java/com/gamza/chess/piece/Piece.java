package com.gamza.chess.piece;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamza.chess.Enum.Color;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public abstract class Piece {

    private int id;
    @JsonIgnore
    private Color color;
    int x;
    int y;
    @JsonIgnore
    boolean live = true;

    public Piece(int id, Color color, int x, int y) {
        this.id = id;
        this.color = color;
        this.x = x;
        this.y = y;

    }

    public abstract List<Map<Integer, Integer>> validMoveList();
    @JsonIgnore
    public abstract boolean isValidMove();
    public abstract void move(int x, int y);
    public abstract void live();
    public abstract void attack(Piece target);
}
