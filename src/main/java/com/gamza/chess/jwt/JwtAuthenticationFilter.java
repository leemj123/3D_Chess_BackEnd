package com.gamza.chess.jwt;

import com.gamza.chess.error.ErrorJwtCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth") || path.contains("/game/start")) {
            filterChain.doFilter(request, response);
            return;
        }
        //만약 프론트가 accessToken에 아무것도 안넣어서 보내면 그건 refresh해달라는 의미
        String accessToken = jwtProvider.resolveAT(request);
        ErrorJwtCode errorCode;
        try {
            if (accessToken != null && jwtProvider.validateToken(accessToken)) {

                String email = jwtProvider.getUserEmail(accessToken);

                UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (MalformedJwtException e) {
            log.info("103 error");
            errorCode = ErrorJwtCode.INVALID_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (ExpiredJwtException e) {
            log.info("101 error");
            errorCode = ErrorJwtCode.EXPIRED_AT;
            setResponse(response, errorCode);
            return;
        } catch (UnsupportedJwtException e) {
            log.info("105 error");
            errorCode = ErrorJwtCode.INVALID_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (IllegalArgumentException e) {
            log.info("104 error");
            errorCode = ErrorJwtCode.EMPTY_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (SignatureException e) {
            log.info("106 error");
            errorCode = ErrorJwtCode.INVALID_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (RuntimeException e) {
            log.info("4006 error");
            errorCode = ErrorJwtCode.JWT_COMPLEX_ERROR;
            setResponse(response, errorCode);
            return;
        }



        filterChain.doFilter(request,response);
    }

    private void setResponse(HttpServletResponse response, ErrorJwtCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());

        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
