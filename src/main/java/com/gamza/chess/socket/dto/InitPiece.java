package com.gamza.chess.socket.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InitPiece {
    private String action;
    private List<PieceInfoDto> initPieces;
}
