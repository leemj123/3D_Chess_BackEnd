package com.gamza.chess.error;

import com.gamza.chess.error.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorExceptionControllerAdvice {
    @ExceptionHandler({JwtException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final JwtException e) {
        log.info("JwtException");
        ErrorEntity errorEntity = ErrorEntity.builder()
                .code(e.getErrorCode().getCode())
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(errorEntity);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final BadRequestException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorEntity.builder()
                        .code(e.getErrorCode().getCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final UnAuthorizedException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorEntity.builder()
                        .code(e.getErrorCode().getCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final NotFoundException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorEntity.builder()
                        .code(e.getErrorCode().getCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final DuplicateException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorEntity.builder()
                        .code(e.getErrorCode().getCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

    @ExceptionHandler({InternerServerException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final InternerServerException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorEntity.builder()
                        .code(e.getErrorCode().getCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<ErrorEntity> exceptionHandler(final InvalidTokenException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorEntity.builder()
                        .code(e.getErrorCode().getCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build());
    }
}
