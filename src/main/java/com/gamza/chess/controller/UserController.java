//package com.gamza.chess.controller;
//
//import com.gamza.chess.dto.LoginRequestDto;
//import com.gamza.chess.dto.SignUpRequestDto;
//import com.gamza.chess.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    @GetMapping("/login")
//    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response){
//        userService.BasicLogin(loginRequestDto, response);
//
//    }
//    @PostMapping("/signUp")
//    public void signUp(SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
//        userService.BasicSignUp(signUpRequestDto, response);
//    }
//}
