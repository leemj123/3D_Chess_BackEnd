package com.gamza.chess.socket.messageform;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserInfoForm {
    private final String color;
    private final String userName;
    private final String tier;
    private final int score;

    public UserInfoForm(String color, Map<String, Object> attributes) {
        this.color = color;
        this.userName = (String) attributes.get("name");
        this.tier = (String) attributes.get("tier");
        this.score = (int) attributes.get("calScore");
    }
}
