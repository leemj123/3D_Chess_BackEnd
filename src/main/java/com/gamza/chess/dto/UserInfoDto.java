package com.gamza.chess.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserInfoDto {
    private final String userName;
    private final Object tier;
    private final int score;

    public UserInfoDto (Map<String,Object> claims) {
        this.userName = (String) claims.get("name");
        this.tier = claims.get("tier");
        this.score = (int) claims.get("calScore");
    }
}
