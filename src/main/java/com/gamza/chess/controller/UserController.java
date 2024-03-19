package com.gamza.chess.controller;

import com.gamza.chess.dto.UserInfoDto;
import com.gamza.chess.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtProvider jwtProvider;
    @GetMapping()
    public UserInfoDto userInfo(HttpServletRequest request) {
        Map<String,Object> claims = jwtProvider.getTokenSubject(request.getHeader("Authorization").substring(7));
        return new UserInfoDto(claims);
    }
}
