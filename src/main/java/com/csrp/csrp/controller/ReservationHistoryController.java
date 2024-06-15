package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.ReservationPageDTO;
import com.csrp.csrp.dto.response.ReservationHistoryListResponseDTO;
import com.csrp.csrp.dto.response.ReservationHistoryResponseDTO;
import com.csrp.csrp.service.ReservationHistoryService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservationHistory")
public class ReservationHistoryController {
  private final ReservationHistoryService reservationHistoryService;

  // 예매 내역 조회
  @GetMapping("/show")
  public ResponseEntity<?> reservationHistoryShow(
      @Validated @RequestBody ReservationPageDTO reservationPageDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    ReservationHistoryListResponseDTO reservationHistoryListResponseDTO = reservationHistoryService.reservationHistoryShow(tokenUserInfo, reservationPageDTO);
    return ResponseEntity.ok().body(reservationHistoryListResponseDTO);
  }


}
