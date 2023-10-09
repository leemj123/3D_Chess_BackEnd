package com.gamza.chess.jwt;


import com.gamza.chess.entity.UserEntity;
import com.gamza.chess.error.ErrorCode;
import com.gamza.chess.error.exception.NotFoundException;
import com.gamza.chess.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Transactional
public class JwtProvider {
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessExpiration}")
    private long ATExpireTime;

    @Value("${jwt.refreshExpiration}")
    private long RTExpireTime ;

    public String createAT(String email){
        return this.createToken(email,ATExpireTime);
    }
    public String createRT(String email){
        return this.createToken(email,RTExpireTime);
    }


    public String createToken(String email, long tokenTime){
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()->{throw new UsernameNotFoundException("찾을 수 없음");});
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles",userEntity.getUserRole().toString());

        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime()+tokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveAT(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null){
            return request.getHeader("Authorization").substring(7);
        }
        return null;
    }
    public String resolveRT(HttpServletRequest request) {
        if (request.getHeader("refreshToken") != null){
            return request.getHeader("refreshToken").substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("JWT token has expired");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty", e);
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new SignatureException("JWT signature verification failed", e);
        }
    }

    public void setHeaderAT(HttpServletResponse response, String AT) {
        response.setHeader("Authorization","Bearer "+AT);
    }
    public void setHeaderRT(HttpServletResponse response, String RT) {
        response.setHeader("refreshToken","Bearer "+RT);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
    public String getUserEmail(String token) {
        String email = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        return email;
    }
    public UserEntity findByUserOnToken(HttpServletRequest request) {
        String userEmail = this.getUserEmail(this.resolveAT(request));
        return userRepository.findByEmail(userEmail).orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION.getMessage(),ErrorCode.NOT_FOUND_EXCEPTION));
    }
}
