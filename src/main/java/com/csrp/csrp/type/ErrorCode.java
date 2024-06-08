package com.csrp.csrp.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  CONCERT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 개최된 콘서트 존재"),
  CONCERT_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 콘서트"),
  DATABASE_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"),
  DATABASE_SELECT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패");

  private final HttpStatus httpStatus;
  private final String errorMessage;
}
