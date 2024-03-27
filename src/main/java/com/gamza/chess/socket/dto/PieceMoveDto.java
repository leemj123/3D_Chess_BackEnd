package com.gamza.chess.socket.dto;

import club.gamza.warpsquare.engine.File;
import club.gamza.warpsquare.engine.Level;
import club.gamza.warpsquare.engine.PieceType;
import club.gamza.warpsquare.engine.Rank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PieceMoveDto {
    private PieceType pieceType;
    private Rank currentRank;
    private File currentFile;
    private Level currentLevel;
    private Rank toMoveRank;
    private File toMoveFile;
    private Level toMoveLevel;
}
