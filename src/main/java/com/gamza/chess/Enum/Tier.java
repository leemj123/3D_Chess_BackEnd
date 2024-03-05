package com.gamza.chess.Enum;

import com.gamza.chess.piece.King;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum Tier {
    Farmer(0,2,false),
    Soldier(3,5,true),
    Veterans(6,9,true),
    Deacon(10,13,false),
    Priests(14,18,true),
    HighPriests(19,23,true),
    Squire(24,28,false),
    Knight(29,35,true),
    Crusader(36,42,true),
    General(43,49,false),
    Duke(50,56,true),
    Royal(57,Integer.MAX_VALUE,false);

    private final int minScore;
    private final int maxScore;
    private final boolean hasDemote;
}
