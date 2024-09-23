package com.gamza.chess.socket.messageform;

import club.gamza.warpsquare.engine.Level;
import com.gamza.chess.Enum.ACTION;
import lombok.Getter;

@Getter
public class BoardSyncForm {
    ACTION action;
    private final Level currentLevel;
    private final Level targetLevel;

    public BoardSyncForm(BoardMoveForm boardMoveForm) {
        this.action = ACTION.SYNC;
        this.currentLevel = boardMoveForm.getCurrentLevel();
        this.targetLevel = boardMoveForm.getTargetLevel();
    }
}
