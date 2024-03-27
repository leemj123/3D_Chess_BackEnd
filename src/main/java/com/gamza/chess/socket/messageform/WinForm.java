package com.gamza.chess.socket.messageform;

import com.gamza.chess.Enum.ACTION;
import com.gamza.chess.Enum.Tier;
import lombok.Builder;

@Builder
public class WinForm {
    private ACTION action;
    private String message;
    private Tier beforeTier;
    private int beforeScore;
    private Tier currentTier;
    private int currentScore;
}
