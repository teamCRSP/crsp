package com.csrp.csrp.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  /**
   * ******************************* Global Error CodeList ***************************************
   * HTTP Status Code
   * 400 : Bad Request
   * 401 : Unauthorized
   * 403 : Forbidden
   * 404 : Not Found
   * 500 : Internal Server Error
   * *********************************************************************************************
   */

  // Request dto 로 데이터가 전달되지 않을 경우
  MISSING_REQUEST_DTO_ERROR(HttpStatus.NOT_FOUND, "I-001", "DTO 값이 없습니다."),

  // USER
  EXISTS_USER_EMAIL(HttpStatus.BAD_REQUEST, "U-001", "이메일이 존재합니다."),
  NOT_EXISTS_USER_EMAIL(HttpStatus.NOT_FOUND, "U-002", "회원 이메일이 존재하지 않습니다."),
  NOT_ACCORD_USER_PASSWORD(HttpStatus.BAD_REQUEST, "U-003", "패스워드가 맞지 않습니다."),
  NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "U-004", "회원이 존재하지 않습니다."),
  NOT_ACCORD_USER_EMAIL(HttpStatus.BAD_REQUEST, "U-005", "이메일이 맞지 않습니다."),

  // REVIEW
  NOT_EXISTS_REVIEW(HttpStatus.NOT_FOUND, "R-001", "리뷰가 존재하지 않습니다.");

  private final HttpStatus httpStatus;
  private final String divisionCode;
  private final String errorMessage;
}
