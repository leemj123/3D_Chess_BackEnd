package com.gamza.chess.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SocketInitMessageDto {
    private String action;
    private List<PieceInfoDto> initPieces;
}
