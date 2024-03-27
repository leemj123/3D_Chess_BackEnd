package com.gamza.chess.socket.messageform;

import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.Enum.Color;
import lombok.Getter;

import java.util.Map;

@Getter
public class RoomInfoForm {
    private final ACTION action;
    private final String roomId;
    private final Color color;
    private final String matchedUserName;
    private final Object matchedUserTier;
    private final int matchedUserScore;

    public RoomInfoForm(String roomId, Color color, Map<String,Object> claims) {
        this.action = ACTION.ROOM_STATE;
        this.roomId = roomId;
        this.color = color;
        this.matchedUserName = (String) claims.get("name");
        this.matchedUserTier = claims.get("tier");
        this.matchedUserScore = (int) claims.get("calScore");
    }
}
