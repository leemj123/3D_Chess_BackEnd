package com.gamza.chess.dto;

import com.gamza.chess.piece.Piece;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PieceInfoDto {
    private int id;
    private int x;
    private int y;
    private boolean hasMoved;
    public PieceInfoDto(Piece piece){
        this.id = piece.getId();
        this.x = piece.getX();
        this.y = piece.getY();
        this.hasMoved = piece.isHasMoved();
    }
    public PieceInfoDto(int i, int x, int y, boolean hasMoved) {
        this.id = i;
        this.x = x;
        this.y = y;
        this.hasMoved = hasMoved;
    }
}
