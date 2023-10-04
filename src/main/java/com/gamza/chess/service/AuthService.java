package com.gamza.chess.service;

import com.gamza.chess.dto.LoginRequestDto;
import com.gamza.chess.dto.SignUpRequestDto;
import com.gamza.chess.entity.UserEntity;
import com.gamza.chess.error.ErrorCode;
import com.gamza.chess.error.exception.JwtException;
import com.gamza.chess.error.exception.UnAuthorizedException;
import com.gamza.chess.jwt.JwtProvider;
import com.gamza.chess.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public void basicLogin (LoginRequestDto loginRequestDto, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->{throw new UnAuthorizedException("존재하지 않는 이메일입니다.", ErrorCode.ACCESS_DENIED_EXCEPTION);});

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword()))
            throw new UnAuthorizedException("비밀번호가 일치하지 않습니다.",ErrorCode.ACCESS_DENIED_EXCEPTION);
        userEntity.resetRT(jwtProvider.createRT(userEntity.getEmail()));
        this.setJwtTokenHeader(loginRequestDto.getEmail(), response);
    }

    public void basicSignUp(SignUpRequestDto signUpRequestDto, HttpServletResponse response) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new UnAuthorizedException("이미 사용중인 이메일 입니다.", ErrorCode.ACCESS_DENIED_EXCEPTION);
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        UserEntity userEntity = signUpRequestDto.toEntity(signUpRequestDto);
        userRepository.save(userEntity);
        this.setJwtTokenHeader(userEntity.getEmail(), response);
    }

    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String RT = jwtProvider.resolveRT(request);
        jwtProvider.validateToken(RT);

        UserEntity userEntity = userRepository.findByEmail(jwtProvider.getUserEmail(RT))
                .orElseThrow(()->{throw new JwtException("complex error, pleas reLogin", ErrorCode.JWT_COMPLEX_ERROR);});
        if (!(RT.equals(userEntity.getRefreshToken())))
            throw new JwtException("invalid refresh token", ErrorCode.INVALID_TOKEN);

        jwtProvider.setHeaderAT(response, jwtProvider.createAT(userEntity.getEmail()));

    }

    private void setJwtTokenHeader(String email, HttpServletResponse response) {
        String AT = jwtProvider.createAT(email);
        String RT = jwtProvider.createRT(email);

        jwtProvider.setHeaderAT(response, AT);
        jwtProvider.setHeaderRT(response, RT);
    }
}