package com.gamza.chess.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SocketPlayerInfoMessage {
    private String action;
    private String color;
}
