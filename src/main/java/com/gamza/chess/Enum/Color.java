package com.gamza.chess.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Color {

    White(0),
    Black(1);
    private final int key;

}
