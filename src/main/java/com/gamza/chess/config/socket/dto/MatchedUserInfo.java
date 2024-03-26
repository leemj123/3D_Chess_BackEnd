package com.gamza.chess.config.socket.dto;

import com.gamza.chess.Enum.ACTION;
import lombok.Getter;

import java.util.Map;

@Getter
public class MatchedUserInfo {
    private final ACTION action;
    private final String userName;
    private final Object tier;
    private final int score;

    public MatchedUserInfo(Map<String,Object> claims) {
        this.action = ACTION.MATCHED_USER;
        this.userName = (String) claims.get("name");
        this.tier = claims.get("tier");
        this.score = (int) claims.get("calScore");
    }
}
