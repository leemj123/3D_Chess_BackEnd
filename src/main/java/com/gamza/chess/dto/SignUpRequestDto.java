package com.gamza.chess.dto;

import com.gamza.chess.Enum.UserRole;
import com.gamza.chess.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignUpRequestDto {
    private String userName;
    private String password;
    private String email;

    public UserEntity toEntity (SignUpRequestDto signUpRequestDto) {
        return UserEntity.builder()
                ._id(String.valueOf(UUID.randomUUID()))
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .userName(signUpRequestDto.getUserName())
                .userRole(UserRole.USER)
                .build();
    }
}
