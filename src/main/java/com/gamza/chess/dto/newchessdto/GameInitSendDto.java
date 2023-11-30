package com.gamza.chess.dto.newchessdto;

import com.gamza.chess.Enum.ACTION;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class GameInitSendDto {
    private String action;
    private List<PieceLocation> LocationList;

    public GameInitSendDto(ACTION action) {
        this.action = action.toString();
        this.LocationList = new ArrayList<>();
    }
}
