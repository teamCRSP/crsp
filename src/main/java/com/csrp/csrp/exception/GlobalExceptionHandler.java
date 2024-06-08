package com.csrp.csrp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.csrp.csrp.dto.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ConcertException.class)
  public ResponseEntity<ErrorResponse> handleConcertException(
      ConcertException e
  ){
    log.warn(
        String.format("[%s][%s] -> %s", e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage()));
    return ResponseEntity.status(e.getHttpStatus())
        .body(new com.csrp.csrp.dto.ErrorResponse());
  }

  @ExceptionHandler(DbException.class)
  public ResponseEntity<ErrorResponse> handleConcertException(
      DbException e
  ){
    log.warn(
        String.format("[%s][%s] -> %s", e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage()));
    return ResponseEntity.status(e.getHttpStatus())
        .body(new com.csrp.csrp.dto.ErrorResponse());
  }
}
