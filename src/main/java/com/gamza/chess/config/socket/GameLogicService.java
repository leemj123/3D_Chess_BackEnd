package com.gamza.chess.config.socket;

import club.gamza.warpsquare.engine.Game;
import club.gamza.warpsquare.engine.Piece;
import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.dto.newchessdto.GameInitSendDto;
import com.gamza.chess.dto.newchessdto.PieceLocation;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GameLogicService {
    Game game = new Game();


//    GameInitSendDto gameInitSendDto = new GameInitSendDto(ACTION.INIT);
//                    gameInitSendDto.setLocationList(getPieceLocationList(game.getPieces()));
//    String gameInitStatusSend = mapper.writeValueAsString(gameInitSendDto);
//
//    private List<PieceLocation> getPieceLocationList (Piece[] pieces) {
//        return Arrays.stream(pieces)
//                .map(piece -> new PieceLocation(piece.getPieceType(),piece.getSquare()))
//                .collect(Collectors.toList());
//    }
}
