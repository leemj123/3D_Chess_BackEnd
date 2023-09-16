package com.gamza.chess.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamza.chess.dto.PieceInfoDto;
import com.gamza.chess.piece.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String GAME_PIECE_KEY = "game:%s:piece:%d";

    public void savePiece(PieceInfoDto pieceInfoDto, String roomId) {
        String key = String.format(GAME_PIECE_KEY,roomId,pieceInfoDto.getId());

        Map<String, String> pieceData = new HashMap<>();
        pieceData.put("x",Integer.toString(pieceInfoDto.getX()));
        pieceData.put("y",Integer.toString(pieceInfoDto.getY()));
        pieceData.put("hasMoved",String.valueOf(false));

        stringRedisTemplate.opsForHash().putAll(key, pieceData);
    }

    public List<PieceInfoDto> getAllInGamePieceList(String roomId){
        List<PieceInfoDto> allOfInGamePieceList = new ArrayList<>();
        for (int i =1; i<33; i++) {
            String key = String.format(GAME_PIECE_KEY,roomId,i);
            Map<Object, Object> redisPieceInfo = stringRedisTemplate.opsForHash().entries(key);
            // redisPieceInfo를 PieceInfoDto로 바꾸고 allOfInGamePieceList에 add
            if (redisPieceInfo != null) {
                PieceInfoDto pieceInfoDto = new PieceInfoDto(i
                        ,Integer.parseInt((String) redisPieceInfo.get("x"))
                        ,Integer.parseInt((String) redisPieceInfo.get("y"))
                        ,Boolean.parseBoolean((String) redisPieceInfo.get("hasMoved"))
                );
                allOfInGamePieceList.add(pieceInfoDto);
            }
        }
        return allOfInGamePieceList;
    }

    public PieceInfoDto updatePiece(String roomId, PieceInfoDto pieceInfoDto) {
        //유저가 움직일 수 있는 기물인지

        String key = String.format(GAME_PIECE_KEY,roomId,pieceInfoDto.getId());

        //기물 정보 가져오기
        Map<Object, Object> pieceInfo = stringRedisTemplate.opsForHash().entries(key);
        if (pieceInfo == null) {
            throw new RuntimeException("Piece data not found for game " + roomId + " and piece " + pieceInfoDto.getId());
        }
        //기물 타입 확인

        //기물 정보 업데이트
        stringRedisTemplate.opsForHash().put(key, "x", Integer.toString(pieceInfoDto.getX()));
        stringRedisTemplate.opsForHash().put(key, "y", Integer.toString(pieceInfoDto.getY()));
        stringRedisTemplate.opsForHash().put(key,"hasMoved",String.valueOf(true));

        return pieceInfoDto;
    }
    public PieceInfoDto checkPieceLocation(String roomId,int id) {
        String key = String.format(GAME_PIECE_KEY,roomId,id);
        Map<Object, Object> redisPieceInfo = stringRedisTemplate.opsForHash().entries(key);
        if (redisPieceInfo == null || redisPieceInfo.isEmpty()) {
            throw new RuntimeException("Piece data not found for game " + roomId + " and piece " + id);
        }
        return new PieceInfoDto(id
                ,Integer.parseInt((String) redisPieceInfo.get("x"))
                ,Integer.parseInt((String) redisPieceInfo.get("y"))
                ,Boolean.parseBoolean((String) redisPieceInfo.get("hasMoved"))
        );
    }
}
