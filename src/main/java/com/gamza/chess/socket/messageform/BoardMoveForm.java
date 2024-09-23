package com.gamza.chess.socket.messageform;

import club.gamza.warpsquare.engine.Level;
import lombok.Getter;

@Getter
public class BoardMoveForm {
    private Level currentLevel;
    private Level targetLevel;
}
