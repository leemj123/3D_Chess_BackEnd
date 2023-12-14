package com.gamza.chess.Enum;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ACTION {
    INIT("InitMethod"),
    COLOR("setColor"),
    MOVE("MovePiece");

    private final String title;
}
