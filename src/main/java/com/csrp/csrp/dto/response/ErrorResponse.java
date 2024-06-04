package com.csrp.csrp.dto.response;

import com.csrp.csrp.type.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor
@Slf4j
public class ErrorResponse {
  private ErrorCode errorCode;
  private HttpStatus httpStatus;  // 에러 상태 코드
  private String divisionCode; // 에러 구분 코드
  private String errorMessage; // 에러 메시지
  private List<FieldError> fieldErrors;  // 검증 에러 메시지

  public ErrorResponse(ErrorCode errorCode, HttpStatus httpStatus, String divisionCode, String errorMessage) {
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
    this.divisionCode = divisionCode;
    this.errorMessage = errorMessage;
  }
  public ErrorResponse(List<FieldError> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  public static class FieldErrorResponse {
    public static List<FieldError> getValidatedResult(BindingResult result) {
      if (result.hasErrors()) { // 입력값이 검증에 걸렸으면
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
          log.warn("invalid client data - {}", fieldError.toString());
        }
        return fieldErrors;
      }
      return null;
    }
  }
}
