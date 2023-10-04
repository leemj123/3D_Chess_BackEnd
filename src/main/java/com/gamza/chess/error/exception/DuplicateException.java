package com.gamza.chess.error.exception;


import com.gamza.chess.error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
