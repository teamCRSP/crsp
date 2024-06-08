package com.csrp.csrp.exception;

//import com.csrp.csrp.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.csrp.csrp.dto.ErrorResponse;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) { // API 호출 시 '객체' 혹은 '파라미터' 데이터 값이 유효하지 않은 경우
    log.error("handleMethodArgumentNotValidException", ex);
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
      log.warn("invalid client data - {}", fieldError.toString());
    }
    ErrorResponse errorResponse = new ErrorResponse(fieldErrors);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(CustomException e) {
    log.error("customException is occurred. ", e);
    return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getErrorCode(), e.getHttpStatus(), e.getDivisionCode(), e.getErrorMessage()));
  }

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
