package com.gamza.chess.repository;

import com.gamza.chess.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByEmail(String userEmail);

    Optional<UserEntity> findByEmail(String username);
}
