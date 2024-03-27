package com.gamza.chess.socket.messageform;

import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.socket.dto.PieceLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class PieceInitSendForm {

    private ACTION action;
    private List<PieceLocation> LocationList;

    public PieceInitSendForm(List<PieceLocation> pieceLocationList) {
        this.action = ACTION.PIECE_STATE;
        this.LocationList = pieceLocationList;
    }
}
