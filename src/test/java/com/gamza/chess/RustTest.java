package com.gamza.chess;

import club.gamza.warpsquare.engine.Game;
import club.gamza.warpsquare.engine.Piece;
import com.gamza.chess.socket.dto.PieceLocation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RustTest {
    @Test
    void a(){

        Game game = new Game();
        Piece[] pieces = game.getPieces();

        List<PieceLocation> pieceLocations = Arrays.stream(pieces)
                .map(piece -> new PieceLocation(piece.getPieceType(),piece.getSquare()))
                .collect(Collectors.toList());

        pieceLocations.stream().forEach(result->System.out.println(result.getPieceType()));

        System.out.println(game.getTurn());
//        game.getAttackSquares()




    }
}
