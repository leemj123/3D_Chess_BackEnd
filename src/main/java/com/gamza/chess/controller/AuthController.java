package com.gamza.chess.controller;

import com.gamza.chess.dto.LoginRequestDto;
import com.gamza.chess.dto.SignUpRequestDto;
import com.gamza.chess.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증관련")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인",description = "응답 헤더에 AT,RT 포함")
    @GetMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        authService.basicLogin(loginRequestDto, response);

    }
    @Operation(summary = "회원가입",description = "응답 헤더에 AT,RT 포함")
    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
        authService.basicSignUp(signUpRequestDto, response);
    }
    @PostMapping("/refresh")
    @Operation(summary = "AT 재발급용",description = "헤더에 AT를 보내줘야함, 응답으로 헤더에 AT만 포함")
    public void refreshAccessToken (HttpServletRequest request, HttpServletResponse response) {
        authService.refreshAccessToken(request, response);
    }

}
