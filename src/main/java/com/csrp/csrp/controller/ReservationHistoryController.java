package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.ReservationRegisterRequestDTO;
import com.csrp.csrp.dto.response.ReservationDetailResponseDTO;
import com.csrp.csrp.dto.response.ReservationListResponseDTO;
import com.csrp.csrp.service.ReservationHistoryService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservationHistory")
public class ReservationHistoryController {
  private final ReservationHistoryService reservationHistoryService;
  // 예매 내역 등록
  @PostMapping("/register")
  public ResponseEntity<?>  reservationRegister(
      @Validated @RequestBody ReservationRegisterRequestDTO reservationRegisterRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    boolean result = reservationHistoryService.ReservationRegister(reservationRegisterRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 예매 내역 상세보기
  @GetMapping("/myReservationDetail")
  public ResponseEntity<?> myReservation(
      @RequestParam("reservationHistoryId") Long reservationHistoryId,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    ReservationDetailResponseDTO reservationDetailResponseDTO = reservationHistoryService.myReservationDetail(reservationHistoryId, tokenUserInfo);
    return ResponseEntity.ok().body(reservationDetailResponseDTO);
  }

  // 예매 내역 리스트
  @GetMapping("/myReservationList")
  public ResponseEntity<?> myReservationList(
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    ReservationListResponseDTO reservationListResponseDTO = reservationHistoryService.myReservationList(tokenUserInfo);
    return ResponseEntity.ok().body(reservationListResponseDTO);
  }

}
