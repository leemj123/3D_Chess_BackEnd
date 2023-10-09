package com.gamza.chess.controller;

import com.gamza.chess.dto.LoginRequestDto;
import com.gamza.chess.dto.SignUpRequestDto;
import com.gamza.chess.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        authService.basicLogin(loginRequestDto, response);

    }
    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
        authService.basicSignUp(signUpRequestDto, response);
    }

    @PatchMapping("/refresh")
    public void refreshAccessToken (HttpServletRequest request, HttpServletResponse response) {
        authService.refreshAccessToken(request, response);
    }
}
