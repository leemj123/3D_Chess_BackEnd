package com.gamza.chess.socket;

import club.gamza.warpsquare.engine.Piece;
import com.gamza.chess.socket.dto.PieceLocation;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class GameLogicService {


//    GameInitSendDto gameInitSendDto = new GameInitSendDto(ACTION.INIT);
//                    gameInitSendDto.setLocationList(getPieceLocationList(game.getPieces()));
//    String gameInitStatusSend = mapper.writeValueAsString(gameInitSendDto);

    public List<PieceLocation> getPieceLocationList (Piece[] pieces) {
        return Arrays.stream(pieces)
                .map(piece -> new PieceLocation(piece.getPieceType(),piece.getSquare()))
                .collect(Collectors.toList());
    }
}
