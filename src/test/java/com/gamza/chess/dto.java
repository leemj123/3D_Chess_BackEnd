package com.gamza.chess;

import club.gamza.warpsquare.engine.*;

public class dto {
    private Rank rank;
    private File file;
    private Level level;

    public dto() {}
    public dto(Square square) {
        this.rank = square.getRank();
        this.file = square.getFile();
        this.level = square.getLevel();
    }
}
