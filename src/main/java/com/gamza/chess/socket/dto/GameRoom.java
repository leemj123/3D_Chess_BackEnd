package com.gamza.chess.socket.dto;

import club.gamza.warpsquare.engine.Game;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class GameRoom {
    private String roomId;
    private Game game;
    private SessionPair sessionPair;

    public GameRoom (SessionPair sessionPair) {
        this.roomId = UUID.randomUUID().toString();
        this.game = new Game();
        this.sessionPair = sessionPair;
    }
}
