package com.gamza.chess.service;

import com.gamza.chess.dto.PieceInfoDto;
import com.gamza.chess.piece.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String GAME_PIECE_KEY = "game:%s:piece:%s";

    public void savePiece(PieceInfoDto pieceInfoDto, String gameId) {
        String key = String.format(GAME_PIECE_KEY,gameId,pieceInfoDto.getId());
        Map<String, String> pieceData = new HashMap<>();
        pieceData.put("x",Integer.toString(pieceInfoDto.getX()));
        pieceData.put("y",Integer.toString(pieceInfoDto.getY()));

        stringRedisTemplate.opsForHash().putAll(key, pieceData);
    }
    public PieceInfoDto updatePiece(String gameId, PieceInfoDto pieceInfoDto) {
        //유저가 움직일 수 있는 기물인지

        String key = String.format(GAME_PIECE_KEY,gameId,pieceInfoDto.getId());

        //기물 정보 가져오기
        String pieceInfo = stringRedisTemplate.opsForValue().get(key);
        if (pieceInfo == null) {
            throw new RuntimeException("Piece data not found for game " + gameId + " and piece " + pieceInfoDto.getId());
        }
        //기물 타입 확인

        //기물 정보 업데이트
        stringRedisTemplate.opsForHash().put(key, "x", Integer.toString(pieceInfoDto.getX()));
        stringRedisTemplate.opsForHash().put(key, "y", Integer.toString(pieceInfoDto.getY()));

        return pieceInfoDto;
    }
}
