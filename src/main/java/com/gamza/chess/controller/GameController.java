package com.gamza.chess.controller;

import com.gamza.chess.piece.Piece;
import com.gamza.chess.service.chessService.GameMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameMainService gameMainService;
    @GetMapping("/basic-start")
    public Piece[][] gameSetting(){
        return gameMainService.settingTowDChessBoard();
    }
}
