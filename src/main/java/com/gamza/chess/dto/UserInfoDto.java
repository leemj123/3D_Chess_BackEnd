package com.gamza.chess.dto;

import com.gamza.chess.Enum.Tier;
import com.gamza.chess.Enum.UserRole;
import lombok.Getter;

import java.util.Map;

@Getter
public class UserInfoDto {
    private String userName;
    private Object tier;
    private int score;

    public UserInfoDto (Map<String,Object> claims) {
        this.userName = (String) claims.get("email");
        this.tier = claims.get("tier");
        this.score = (int) claims.get("score");
    }
}
