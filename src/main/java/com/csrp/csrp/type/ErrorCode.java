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

  // CONCERT
  CONCERT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"C-001", "이미 개최된 콘서트 존재"),
  CONCERT_NOT_FOUND(HttpStatus.BAD_REQUEST,"C-002", "존재하지 않는 콘서트"),
  CONCERT_DATE_NOT_VALID(HttpStatus.BAD_REQUEST, "C-003", "종료일이 개최일 보다 이전입니다"),
  CONCERT_LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "C-004", "콘서트 위치가 존재안함"),
  CONCERT_DATE_NOT_FOUND(HttpStatus.BAD_REQUEST, "C-005", "콘서트 날짜 존재 안함"),
  CONCERT_SEAT_NOT_FOUND(HttpStatus.BAD_REQUEST, "C-006", "콘서트 좌석 존재 안함"),
  DISCOUNT_DATE_NOT_VALID(HttpStatus.BAD_REQUEST, "C-007", "할인 날짜를 잘못 입력"),
  DISCOUNT_PRICE_NOT_VALID(HttpStatus.BAD_REQUEST, "C-008", "할인 금액이 콘서트 금액보다 큼"),
  CONCERT_LOCATION_NOT_MATCH(HttpStatus.BAD_REQUEST, "C-009", "콘서트 제목이 일치 안함"),
  DATABASE_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"D-001", "업데이트 실패"),
  DATABASE_SELECT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"D-002", "조회 실패"),

  // Request dto 로 데이터가 전달되지 않을 경우
  MISSING_REQUEST_DTO_ERROR(HttpStatus.NOT_FOUND, "ALL-001", "DTO 값이 없습니다."),

  // USER
  EXISTS_USER_EMAIL(HttpStatus.BAD_REQUEST, "U-001", "이메일이 존재합니다."),
  NOT_EXISTS_USER_EMAIL(HttpStatus.NOT_FOUND, "U-002", "회원 이메일이 존재하지 않습니다."),
  NOT_ACCORD_USER_PASSWORD(HttpStatus.BAD_REQUEST, "U-003", "패스워드가 맞지 않습니다."),
  NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "U-004", "회원이 존재하지 않습니다."),
  NOT_ACCORD_USER_EMAIL(HttpStatus.BAD_REQUEST, "U-005", "이메일이 맞지 않습니다."),

  // REVIEW
  NOT_EXISTS_REVIEW(HttpStatus.NOT_FOUND, "R-001", "리뷰가 존재하지 않습니다."),

  // INQUIRY
  NOT_EXISTS_INQUIRY(HttpStatus.NOT_FOUND, "I-001", "문의가 없습니다."),

  // ANSWER
  NOT_EXISTS_ANSWER(HttpStatus.NOT_FOUND, "A-001", "답변이 없습니다."),

  // ReportAccepted
  REVIEW_STOP(HttpStatus.FOUND, "RA-001", "3번 이상 신고가 누적 되어 리뷰 등록이 정지되었습니다."),

  // RESERVATION_HISTORY
  NOT_EXISTS_RESERVATION_HISTORY(HttpStatus.NOT_FOUND, "RH-001", "찾는 예매 내역이 없습니다."),

  // TICKET
  NOT_EXISTS_TICKET(HttpStatus.NOT_FOUND, "T-001", "티켓이 없습니다."),

  // RESERVATION_DEAIL
  NOT_EXISTS_RESERVATION_DETAIL(HttpStatus.NOT_FOUND, "RD-001", "찾는 상세 예매 내역이 없습니다."),

  // PAYMENT_HISTORY
  NOT_EXISTS_PAYMENT_HISTORY(HttpStatus.NOT_FOUND, "PH-001", "찾는 결제 내역이 없습니다.");

  private final HttpStatus httpStatus;
  private final String divisionCode;
  private final String errorMessage;
}
