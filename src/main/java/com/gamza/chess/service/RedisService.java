package com.gamza.chess.service;

import com.gamza.chess.dto.PieceInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String GAME_PIECE_KEY = "game:%s:piece:%d";
    private static final String QUEUE_KEY = "match:queue";
    private static final String PLAYER_KEY = "playerId:%s";

    public void establishedGame(String roomKey, String player1, String player2) {
        String player1Key = String.format(PLAYER_KEY,player1);
        Map<String,String> player1Info = new HashMap<>();
        player1Info.put("room",roomKey);
        player1Info.put("color","white");
        player1Info.put("turn","true");
        stringRedisTemplate.opsForHash().putAll(player1Key, player1Info);

        String player2Key = String.format(PLAYER_KEY,player2);
        Map<String,String> player2Info = new HashMap<>();
        player1Info.put("room",roomKey);
        player2Info.put("color","black");
        player2Info.put("turn","false");
        stringRedisTemplate.opsForHash().putAll(player2Key, player2Info);
    }
    public void setPlayerTurn(UUID playerID, boolean turn){

    }
    //======매치 큐 관련 ========================
//    public String enterQueue(UUID userUid) {
//        ListOperations<String,String> waitQueue = stringRedisTemplate.opsForList();
//        waitQueue.rightPush(QUEUE_KEY, userUid.toString());
//
//        if (waitQueue.size(QUEUE_KEY) >=2 ) {
//            UUID player1 = UUID.fromString(waitQueue.leftPop(QUEUE_KEY));
//            UUID player2 = UUID.fromString(waitQueue.leftPop(QUEUE_KEY));
//
//            //startGame(player1, player2);
//        }
//
//        return "enter to queue, And wait to match";
//    }

    //====================기물 로직 관련==========================

    public void savePiece(PieceInfoDto pieceInfoDto, String roomKey) {
        String key = String.format(GAME_PIECE_KEY,roomKey,pieceInfoDto.getId());

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

    public PieceInfoDto updatePiece(String roomKey, PieceInfoDto pieceInfoDto) {
        //유저가 움직일 수 있는 기물인지

        String key = String.format(GAME_PIECE_KEY,roomKey,pieceInfoDto.getId());

        //기물 정보 가져오기
        Map<Object, Object> pieceInfo = stringRedisTemplate.opsForHash().entries(key);
        if (pieceInfo == null) {
            throw new RuntimeException("Piece data not found for game " + roomKey + " and piece " + pieceInfoDto.getId());
        }
        //기물 타입 확인

        //기물 정보 업데이트
        stringRedisTemplate.opsForHash().put(key, "x", Integer.toString(pieceInfoDto.getX()));
        stringRedisTemplate.opsForHash().put(key, "y", Integer.toString(pieceInfoDto.getY()));
        stringRedisTemplate.opsForHash().put(key,"hasMoved",String.valueOf(true));

        return pieceInfoDto;
    }
    public PieceInfoDto checkPieceLocation(String roomKey,int id) {
        String key = String.format(GAME_PIECE_KEY,roomKey,id);
        Map<Object, Object> redisPieceInfo = stringRedisTemplate.opsForHash().entries(key);
        if (redisPieceInfo == null || redisPieceInfo.isEmpty()) {
            throw new RuntimeException("Piece data not found for game " + roomKey + " and piece " + id);
        }
        return new PieceInfoDto(id
                ,Integer.parseInt((String) redisPieceInfo.get("x"))
                ,Integer.parseInt((String) redisPieceInfo.get("y"))
                ,Boolean.parseBoolean((String) redisPieceInfo.get("hasMoved"))
        );
    }
}
