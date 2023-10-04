package com.gamza.chess.entity;

import com.gamza.chess.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String uid;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private UserRole userRole;
    @Column(nullable = false)
    private String refreshToken;

    public void resetRT(String RT) {
        this.refreshToken = RT;
    }

}
