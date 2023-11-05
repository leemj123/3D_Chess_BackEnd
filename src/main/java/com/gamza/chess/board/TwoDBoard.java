package com.gamza.chess.board;

import com.gamza.chess.piece.Piece;
import lombok.Getter;

import java.util.List;

@Getter
public class TwoDBoard {
    public final int MAX_X = 8;
    public final int MAX_Y = 8;
    private List<Piece> board;
    public void inItBoard(List<Piece> board) {
        this.board = board;
    }

}
