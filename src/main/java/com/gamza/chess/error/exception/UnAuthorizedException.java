package com.gamza.chess.error.exception;


import com.gamza.chess.error.ErrorCode;

public class UnAuthorizedException extends BusinessException {

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
