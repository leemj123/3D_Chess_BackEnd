//package com.gamza.chess.dto;
//
//import com.gamza.chess.Enum.UserRole;
//import com.gamza.chess.entity.UserEntity;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class SignUpRequestDto {
//    private String username;
//    private String password;
//    private String userEmail;
//
//    public UserEntity toEntity (SignUpRequestDto signUpRequestDto, UserRole userRole) {
//        return UserEntity.builder()
//                .userEmail(signUpRequestDto.getUserEmail())
//                .password(signUpRequestDto.getPassword())
//                .userName(signUpRequestDto.getUsername())
//                .userRole(userRole)
//                .build();
//    }
//}
