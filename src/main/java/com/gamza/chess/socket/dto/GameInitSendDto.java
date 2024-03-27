package com.gamza.chess.socket.dto;

import com.gamza.chess.Enum.ACTION;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class GameInitSendDto {

    private ACTION action;
    private List<PieceLocation> LocationList;

    public GameInitSendDto(List<PieceLocation> pieceLocationList) {
        this.action = ACTION.INIT;
        this.LocationList = pieceLocationList;
    }
}
