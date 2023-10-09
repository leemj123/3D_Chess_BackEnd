package com.gamza.chess.entity;

import com.gamza.chess.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String _id;
    private String email;
    private String password;
    private String userName;
    private UserRole userRole;
    private String refreshToken;

    public void resetRT(String RT) {
        this.refreshToken = RT;
    }

}
