package com.csrp.csrp.exception;

import com.csrp.csrp.type.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException{
  private ErrorCode errorCode;
  private HttpStatus httpStatus;  // 에러 상태 코드
  private String divisionCode; // 에러 구분 코드
  private String errorMessage; // 에러 메시지

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.httpStatus = errorCode.getHttpStatus();
    this.errorMessage = errorCode.getErrorMessage();
    this.divisionCode = errorCode.getDivisionCode();
  }


}
