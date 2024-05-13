package com.gamza.chess;

import club.gamza.warpsquare.engine.Game;
import club.gamza.warpsquare.engine.Piece;
import club.gamza.warpsquare.engine.PieceMove;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.socket.dto.PieceLocation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RustTest {
    @Test
    void a() throws JsonProcessingException {

        Game game = new Game();
        Piece[] pieces = game.getPieces();

        ObjectMapper obj = new ObjectMapper();
//        String a = obj.writeValueAsString(pieces);
//        System.out.println(a);
        int a = game.getTurn().getValue();
        System.out.println(a);
//        List<PieceLocation> pieceLocations = Arrays.stream(pieces)
//                .map(piece -> new PieceLocation(piece.getPieceType(),piece.getSquare()))
//                .collect(Collectors.toList());
//
//        pieceLocations.stream().forEach(result->System.out.println(result.getPieceType()));
//
//        System.out.println(game.getTurn());

//        game.getAttackSquares();
//        game.legalPieceMove();
//        game.getCapturedPieces();
//        game.getHalfMoveClock();
//        game.getFullMoveNumber();
//        game.
//
//        game.legalPieceMove();
//        PieceMove pieceMove = new


    }
}
