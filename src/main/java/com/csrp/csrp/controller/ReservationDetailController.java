package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.ReservationDetailRegisterRequestDTO;
import com.csrp.csrp.dto.response.ReservationDetailResponseDTO;
import com.csrp.csrp.service.ReservationDetailService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservationDetail")
public class ReservationDetailController {
  private final ReservationDetailService reservationDetailService;
  // 예매 내역 상세 등록
  @PostMapping("/register")
  public ResponseEntity<?>  reservationRegister(
      @Validated @RequestBody List<ReservationDetailRegisterRequestDTO> reservationDetailRegisterRequestDTOList,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    boolean result = reservationDetailService.reservationDetailRegister(reservationDetailRegisterRequestDTOList, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }


  // 예매 내역 상세보기
  @GetMapping("/show")
  public ResponseEntity<?> reservationDetail(
      @RequestParam("reservationDetailId") Long reservationDetailId,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    ReservationDetailResponseDTO reservationDetailResponseDTO = reservationDetailService.myReservationDetail(reservationDetailId, tokenUserInfo);
    return ResponseEntity.ok().body(reservationDetailResponseDTO);
  }

  // 예매 내역 상세 리스트
  @GetMapping("/listShow")
  public ResponseEntity<?> reservationDetailList(
      @RequestParam("reservationHistoryId") Long reservationHistoryId,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    List<ReservationDetailResponseDTO> reservationDetailResponseDTOList = reservationDetailService.reservationDetailList(reservationHistoryId, tokenUserInfo);
    return ResponseEntity.ok().body(reservationDetailResponseDTOList);
  }

}
