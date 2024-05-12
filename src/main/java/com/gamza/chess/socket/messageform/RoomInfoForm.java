package com.gamza.chess.socket.messageform;

import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.Enum.Color;
import com.gamza.chess.socket.dto.SessionPair;
import lombok.Getter;

@Getter
public class RoomInfoForm {
    private final ACTION action;
    private final String roomId;
    private final UserInfoForm myInfo;
    private final UserInfoForm matchedUserInfo;


    public RoomInfoForm(String roomId, Color color, SessionPair sessionPair) {
        this.action = ACTION.ROOM_STATE;
        this.roomId = roomId;
        switch (color.getKey()) {
            case 0:
                this.myInfo = new UserInfoForm(color.name() ,sessionPair.getWhite().getAttributes());
                this.matchedUserInfo = new UserInfoForm(Color.Black.name(), sessionPair.getBlack().getAttributes());
                break;
            case 1:
                this.myInfo = new UserInfoForm(color.name(), sessionPair.getBlack().getAttributes());
                this.matchedUserInfo = new UserInfoForm(Color.White.name(), sessionPair.getWhite().getAttributes());
                break;
            default: throw new RuntimeException("roomInfo exception");

        }

    }
}
/*
룸 정보 리턴

 */