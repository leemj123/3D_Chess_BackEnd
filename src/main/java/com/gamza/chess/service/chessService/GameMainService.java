package com.gamza.chess.service.chessService;

import com.gamza.chess.piece.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMainService {
    private final ChessBoardSetting chessBoardSetting;
    public Piece[][] settingTowDChessBoard() {
        final int MAX_X = 8;
        final int MAX_Y = 8;
        return chessBoardSetting.setting(MAX_X, MAX_Y);
    }
}
