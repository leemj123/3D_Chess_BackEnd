//package com.gamza.chess.controller;
//
//import com.gamza.chess.board.Point;
//import com.gamza.chess.dto.PieceInfoDto;
//import com.gamza.chess.service.RedisService;
//import com.gamza.chess.service.gameservice.GameMainService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/game")
//@RequiredArgsConstructor
//public class GameController {
//    private final GameMainService gameMainService;
//    private final RedisService redisService;
//
//    @GetMapping("/roomset")
//    public String roomSet(){
//        return String.valueOf(UUID.randomUUID());
//    }
//    @PostMapping("/init")
//    public PieceInfoDto[][] gameSetting(@RequestHeader("Room")String roomId){
//        return gameMainService.setting2DChessBoard(roomId);
//    }
//    @GetMapping("/piece/valid-list/{id}")
//    public List<Point> getMoveValidList(@RequestHeader("Room")String roomId, @PathVariable int id) {
//        return gameMainService.getMoveValidList(roomId,id);
//    }
//    @PostMapping("piece/move")
//    public PieceInfoDto movePiece(@RequestHeader("Room")String roomId, @RequestBody PieceInfoDto pieceInfoDto) {
//        return gameMainService.movePiece(roomId,pieceInfoDto);
//    }
//    @GetMapping("piece/info/{id}")
//    public PieceInfoDto whereIsMyPiece(@RequestHeader("Room")String roomId, @PathVariable int id) {
//        return redisService.checkPieceLocation(roomId,id);
//    }
//
//}
