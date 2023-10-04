package com.gamza.chess.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ErrorEntity {

    private int code;
    private String errorMessage;

    @Builder
    public ErrorEntity(HttpStatus httpStatus, int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
