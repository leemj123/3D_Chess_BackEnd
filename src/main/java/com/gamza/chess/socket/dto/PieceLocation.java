package com.gamza.chess.socket.dto;

import club.gamza.warpsquare.engine.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PieceLocation {
    private PieceType pieceType;
    private Rank rank;
    private File file;
    private Level level;

    public PieceLocation (PieceType pieceType,Square pieceSquare) {
        this.pieceType = pieceType;
        this.rank = pieceSquare.getRank();
        this.file = pieceSquare.getFile();
        this.level = pieceSquare.getLevel();
    }
}
