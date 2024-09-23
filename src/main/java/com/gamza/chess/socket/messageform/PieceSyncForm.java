package com.gamza.chess.socket.messageform;

import club.gamza.warpsquare.engine.File;
import club.gamza.warpsquare.engine.Level;
import club.gamza.warpsquare.engine.PieceType;
import club.gamza.warpsquare.engine.Rank;
import com.gamza.chess.Enum.ACTION;
import lombok.Getter;

@Getter
public class PieceSyncForm {
    ACTION action;
    private final PieceType pieceType;
    private final Rank currentRank;
    private final File currentFile;
    private final Level currentLevel;
    private final Rank toMoveRank;
    private final File toMoveFile;
    private final Level toMoveLevel;

    public PieceSyncForm(PieceMoveForm pieceMoveForm) {
        action = ACTION.SYNC;
        pieceType = pieceMoveForm.getPieceType();
        currentRank = pieceMoveForm.getCurrentRank();
        currentFile = pieceMoveForm.getCurrentFile();
        currentLevel = pieceMoveForm.getCurrentLevel();
        toMoveRank = pieceMoveForm.getToMoveRank();
        toMoveFile = pieceMoveForm.getToMoveFile();
        toMoveLevel = pieceMoveForm.getToMoveLevel();
    }

}
