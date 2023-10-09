package com.gamza.chess.dto;

import com.gamza.chess.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
