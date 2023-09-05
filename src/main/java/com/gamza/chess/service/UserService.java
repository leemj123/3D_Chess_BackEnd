//package com.gamza.chess.service;
//
//import com.gamza.chess.Enum.UserRole;
//import com.gamza.chess.dto.LoginRequestDto;
//import com.gamza.chess.dto.SignUpRequestDto;
//import com.gamza.chess.entity.UserEntity;
//import com.gamza.chess.jwt.JwtTokenProvider;
//import com.gamza.chess.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public void BasicLogin(LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        if (!userRepository.existsByUserEmail(loginRequestDto.getUserEmail())) {
//            throw new RuntimeException();
//        }
//
//        UserEntity userEntity = userRepository.findByUserEmail(loginRequestDto.getUserEmail());
//
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())){
//            throw new RuntimeException();
//        }
//        String accessToken = jwtTokenProvider.generateAccessToken(loginRequestDto.getUserEmail());
//        String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequestDto.getUserEmail());
//
//        response.setHeader("Authorization","Bearer " + accessToken);
//        response.setHeader("RefreshToken","Bearer "+ refreshToken);
//
//    }
//    @Transactional
//    public void BasicSignUp(SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
//        if (userRepository.existsByUserEmail(signUpRequestDto.getUserEmail())) {
//            throw new RuntimeException();
//        }
//
//        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
//        UserEntity userEntity = signUpRequestDto.toEntity(signUpRequestDto, UserRole.USER);
//        userRepository.save(userEntity);
//
//        String accessToken = jwtTokenProvider.generateAccessToken(signUpRequestDto.getUserEmail());
//        String refreshToken = jwtTokenProvider.generateRefreshToken(signUpRequestDto.getUserEmail());
//
//        response.setHeader("Authorization","Bearer " + accessToken);
//        response.setHeader("RefreshToken","Bearer "+ refreshToken);
//    }
//}
