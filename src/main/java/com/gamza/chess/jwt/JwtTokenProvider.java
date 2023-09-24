//package com.gamza.chess.jwt;
//
//import io.jsonwebtoken.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import java.util.Date;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class JwtTokenProvider {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.access-expiration}")
//    private long accessTokenExpiration;
//
//    @Value("${jwt.refresh-expiration}")
//    private long refreshTokenExpiration;
//
//
//    public String generateAccessToken(String email) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//
//    public String generateRefreshToken(String email) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);
//
//        return Jwts.builder()
//                .setSubject(email)
//                .claim("type", "refresh")
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//
//    //헤더에 있는 AT토큰 가져오기
//    @Transactional
//    public String resolveAccessToken(HttpServletRequest request) {
//        if (request.getHeader("Authorization") != null && !request.getHeader("Authorization").trim().isEmpty() )
//            return request.getHeader("Authorization").substring(7);
//        return null;
//    }
//    @Transactional
//    public String resolveRefreshToken(HttpServletRequest request){
//        if (request.getHeader("RefreshToken") != null)
//            return request.getHeader("RefreshToken").substring(7);
//        return null;
//    }
//
//   public String getUserEmailFromAccessToken(String token) {
//       String temp = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//       return temp;
//   }
//
//    public String getUsernameFromRefreshToken(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//
//    public boolean validateToken(HttpServletResponse response, String token){
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (MalformedJwtException e) {
//            throw new MalformedJwtException("Invalid JWT token");
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtException(null, null, "Token has expired");
//        } catch (UnsupportedJwtException e) {
//            throw new UnsupportedJwtException("JWT token is unsupported");
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("JWT claims string is empty");
//        } catch (io.jsonwebtoken.SignatureException e) {
//            throw new io.jsonwebtoken.SignatureException("JWT signature does not match");
//        }
//    }
//    public boolean validateRefreshToken(HttpServletResponse response, String token){
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (MalformedJwtException e) {
//            throw new MalformedJwtException("Invalid JWT token");
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtException(null, null, "Token has expired");
//        } catch (UnsupportedJwtException e) {
//            throw new UnsupportedJwtException("JWT token is unsupported");
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("JWT claims string is empty");
//        } catch (io.jsonwebtoken.SignatureException e) {
//            throw new SignatureException("JWT signature does not match");
//        }
//    }
//
//    public String refreshAccessToken(String token, HttpServletResponse response) {
//
//        validateRefreshToken(response, token);
//
//        String userEmail = getUsernameFromRefreshToken(token);
//        return userEmail;
//    }
//}