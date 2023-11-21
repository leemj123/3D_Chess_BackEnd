package com.gamza.chess.error;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCode {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 400, "400 Bad Request"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 401, "인증 오류 발생"),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, 403, "403 Forbidden"),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 404, "404 Not Found"),

    DUPLICATE_EXCEPTION(HttpStatus.BAD_REQUEST,401,"Duplicate email"),
    NON_MATCH_PW(HttpStatus.UNAUTHORIZED,401,"non match password"),
    NON_EXITS_EMAIL(HttpStatus.UNAUTHORIZED,401,"non exits email"),

    EXPIRED_AT(HttpStatus.UNAUTHORIZED,101, "access token has expired. Please try with token refresh"),

    EXPIRED_RT(HttpStatus.UNAUTHORIZED,102, "refresh token has expired. Please Retry login"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,103, "Invalid JWT token."),

    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED,104, "Token cannot has been null"),

    JWT_COMPLEX_ERROR(HttpStatus.UNAUTHORIZED,4006, "JWT Complex error, Please reLogin And call BackEnd");

    private final HttpStatus status;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
