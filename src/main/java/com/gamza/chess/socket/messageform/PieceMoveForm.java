package com.gamza.chess.socket.messageform;

import club.gamza.warpsquare.engine.File;
import club.gamza.warpsquare.engine.Level;
import club.gamza.warpsquare.engine.PieceType;
import club.gamza.warpsquare.engine.Rank;
import lombok.Getter;

@Getter
public class PieceMoveForm {
    private PieceType pieceType;
    private Rank currentRank;
    private File currentFile;
    private Level currentLevel;
    private Rank toMoveRank;
    private File toMoveFile;
    private Level toMoveLevel;

}
