package com.gamza.chess.piece;

import com.gamza.chess.board.Point;
import com.gamza.chess.dto.PieceInfoDto;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(int id, int x, int y) {
        super(id, x, y);
    }
    public Rook(PieceInfoDto pieceInfoDto) {
        super(pieceInfoDto);
    }


    @Override
    public List<Point> getValidMoveList(List<PieceInfoDto> allInGamePieceList) {
        List<Point> validMoves = new ArrayList<>();

        // 4개의 방향을 정의합니다: 위, 아래, 왼쪽, 오른쪽
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            for (int i = 1; i < MAX_X; i++) {
                Point movePoint = new Point(this.getX() + i * dx, this.getY() + i * dy);

                if (!isWithinBounds(movePoint)) {
                    break;  // 체스판을 벗어난 경우 다음 방향으로 이동합니다.
                }

                if (isSquareEmpty(movePoint, allInGamePieceList)) {
                    validMoves.add(movePoint);
                } else {
                    // 다른 기물을 만났을 때, 그 기물이 적군 기물인지 확인합니다.
                    if (isEnemyPiece(movePoint, allInGamePieceList)) {
                        validMoves.add(movePoint);  // 적군 기물을 처치하고 해당 위치에 있을 수 있습니다.
                    }
                    break;  // 더 이상 그 방향으로 움직일 수 없으므로 다음 방향으로 이동합니다.
                }
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
