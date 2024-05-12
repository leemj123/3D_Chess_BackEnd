package com.gamza.chess.piece;

import com.gamza.chess.Enum.Color;
import com.gamza.chess.board.Point;
import com.gamza.chess.dto.PieceInfoDto;
import lombok.Getter;
import java.util.List;

@Getter
public abstract class Piece {

    private int id;
    private int x;
    private int y;
    static final int MAX_X = 8;
    static final int MAX_Y = 8;
    private Color color;
    public boolean hasMoved;

    public Piece(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.hasMoved = false;
    }
    public Piece (PieceInfoDto pieceInfoDto){
        this.id = pieceInfoDto.getId();
        this.x = pieceInfoDto.getX();
        this.y = pieceInfoDto.getY();
        this.hasMoved = pieceInfoDto.isHasMoved();
        this.color = Piece.getColorFromId(pieceInfoDto.getId());
    }


    public abstract List<Point> getValidMoveList(List<PieceInfoDto> allInGamePieceList);

    public abstract boolean isValidMove(PieceInfoDto pieceInfoDto, List<PieceInfoDto> allInGamePieceList);

    public abstract void move();

    public static Color getColorFromId(int id){
        if (id < 17) {
            return Color.Black;
        } else {
            return Color.White;
        }
    }
}
