package com.gamza.chess.service;

import com.gamza.chess.dto.LoginRequestDto;
import com.gamza.chess.dto.SignUpRequestDto;
import com.gamza.chess.entity.UserEntity;
import com.gamza.chess.error.ErrorCode;
import com.gamza.chess.error.exception.DuplicateException;
import com.gamza.chess.error.exception.JwtException;
import com.gamza.chess.error.exception.UnAuthorizedException;
import com.gamza.chess.jwt.JwtProvider;
import com.gamza.chess.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> test(HttpServletRequest request) {
        String AT = request.getHeader("Authorization");
        return ResponseEntity.ok("헤더에는 이 값이 담겨 넘어왔어요!: " +AT);
    }

    public void basicLogin (LoginRequestDto loginRequestDto, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->{throw new UnAuthorizedException(ErrorCode.NON_EXITS_EMAIL.getMessage(), ErrorCode.NON_EXITS_EMAIL);});

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword()))
            throw new UnAuthorizedException(ErrorCode.NON_MATCH_PW.getMessage(), ErrorCode.NON_MATCH_PW);
        userEntity.resetRT(jwtProvider.createRT(userEntity.getEmail()));
        userRepository.save(userEntity);
        this.setJwtTokenHeader(loginRequestDto.getEmail(), response);
    }

    public void basicSignUp(SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
        if (signUpRequestDto.getEmail().length() < 6 || signUpRequestDto.getPassword().length() < 6) {
            throw new UnAuthorizedException(ErrorCode.TOO_SHORT_EMAIL_PW.getMessage(), ErrorCode.TOO_SHORT_EMAIL_PW);
        }
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new DuplicateException(ErrorCode.DUPLICATE_EXCEPTION.getMessage(), ErrorCode.DUPLICATE_EXCEPTION);
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        UserEntity userEntity = new UserEntity(signUpRequestDto);
        userRepository.save(userEntity);
        this.setJwtTokenHeader(userEntity.getEmail(), response);
    }

    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String RT = jwtProvider.resolveRT(request);
        jwtProvider.validateToken(RT);

        UserEntity userEntity = userRepository.findByEmail(jwtProvider.getUserEmail(RT))
                .orElseThrow(()->{throw new JwtException(ErrorCode.JWT_COMPLEX_ERROR.getMessage(), ErrorCode.JWT_COMPLEX_ERROR);});
        if (!(RT.equals(userEntity.getRefreshToken())))
            throw new JwtException(ErrorCode.INVALID_TOKEN.getMessage(), ErrorCode.INVALID_TOKEN);

        jwtProvider.setHeaderAT(response, jwtProvider.createAT(userEntity.getEmail()));

    }

    private void setJwtTokenHeader(String email, HttpServletResponse response) {
        String AT = jwtProvider.createAT(email);
        String RT = jwtProvider.createRT(email);

        jwtProvider.setHeaderAT(response, AT);
        jwtProvider.setHeaderRT(response, RT);
    }
}