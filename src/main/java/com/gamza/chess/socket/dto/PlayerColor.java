package com.gamza.chess.socket.dto;

import com.gamza.chess.Enum.ACTION;
import lombok.Getter;

@Getter
public class PlayerColor {
    private final ACTION action;
    private final String color;

    public PlayerColor(String color) {
        this.action = ACTION.COLOR;
        this.color = color;
    }
}
