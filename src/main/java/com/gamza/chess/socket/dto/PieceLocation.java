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
    private Square square;

    public PieceLocation (PieceType pieceType,Square pieceSquare) {
        this.pieceType = pieceType;
        this.square = pieceSquare;
    }
}
