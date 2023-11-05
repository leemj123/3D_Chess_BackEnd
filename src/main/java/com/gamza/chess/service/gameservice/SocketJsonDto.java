package com.gamza.chess.service.gameservice;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SocketJsonDto {
    String roomKey;
    String action;
    int pieceId;
    int toX;
    int toY;
}
