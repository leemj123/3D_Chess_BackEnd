package com.gamza.chess.Enum;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {

    USER("ROLE_USER", 0),
    ADMIN("ROLE_ADMIN", 1);
    private final String title;
    private final int key;
}
