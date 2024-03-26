package com.gamza.chess.piece;

import com.gamza.chess.Enum.Color;
import com.gamza.chess.board.Point;
import com.gamza.chess.config.socket.dto.PieceInfoDto;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Pawn extends Piece{

    public Pawn(int id, int x, int y) {
        super(id, x, y);
    }
    public Pawn(PieceInfoDto pieceInfoDto) {
        super(pieceInfoDto);
    }

    @Override
    public List<Point> getValidMoveList(List<PieceInfoDto> allInGamePieceList) {
        List<Point> validMoves = new ArrayList<>();

        int direction;

        if (this.getColor().equals(Color.Black)) { //흑
            direction = 1;
        }
        else if(this.getColor().equals(Color.White)) {// 백
            direction = -1;
        }
        else throw new RuntimeException();

        // 한 칸 전진
        Point oneStepMove = new Point(this.getX(), this.getY() + direction);
        if (isSquareEmpty(oneStepMove, allInGamePieceList) && isWithinBounds(oneStepMove)) {
            validMoves.add(oneStepMove);

            // 두 칸 전진
            if (!this.isHasMoved()) {
                Point twoStepMove = new Point(this.getX(), this.getY() + (2 * direction));
                if (isSquareEmpty(twoStepMove, allInGamePieceList) && isWithinBounds(twoStepMove)) {
                    validMoves.add(twoStepMove);
                }
            }
        }

        // 대각선 움직임
        for (int dx = -1; dx <= 1; dx += 2) {
            Point diagonalMove = new Point(this.getX() + dx, this.getY() + direction);
            if (isEnemyPiece(diagonalMove, allInGamePieceList) && isWithinBounds(diagonalMove)) {
                validMoves.add(diagonalMove);
            }
        }

        return validMoves;
    }

    @Override
    public boolean isValidMove(PieceInfoDto pieceInfoDto, List<PieceInfoDto> allInGamePieceList) {
        List<Point> pointList = this.getValidMoveList(allInGamePieceList);
        return pointList.stream().anyMatch(piece-> piece.getX() == pieceInfoDto.getX() && piece.getY() == pieceInfoDto.getY());
    }

    @Override
    public void move() {

    }
    private boolean isSquareEmpty(Point point, List<PieceInfoDto> allInGamePieceList) {
        return allInGamePieceList.stream()
                .noneMatch(piece -> piece.getX() == point.getX() && piece.getY() == point.getY());
    }

    private boolean isEnemyPiece(Point point, List<PieceInfoDto> allInGamePieceList) {
        return allInGamePieceList.stream()
                .anyMatch(piece -> piece.getX() == point.getX() && piece.getY() == point.getY() && !getColor().equals(Piece.getColorFromId(piece.getId())));
    }

    private boolean isWithinBounds(Point point) {
        return point.getX() >= 0 && point.getX() < MAX_X && point.getY() >= 0 && point.getY() < MAX_Y;
    }
}
