package com.gamza.chess.controller;

import com.gamza.chess.dto.PieceInfoDto;
import com.gamza.chess.piece.Piece;
import com.gamza.chess.service.chessService.GameMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameMainService gameMainService;
    @GetMapping("/roomset")
    public String roomSet(){
        return String.valueOf(UUID.randomUUID());
    }
    @PostMapping("/init")
    public PieceInfoDto[][] gameSetting(@RequestHeader("Room")String roomId){
        return gameMainService.setting2DChessBoard(roomId);
    }

}
