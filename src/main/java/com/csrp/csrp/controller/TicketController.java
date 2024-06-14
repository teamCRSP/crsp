package com.csrp.csrp.controller;

import com.csrp.csrp.dto.response.TicketResponseDTO;
import com.csrp.csrp.service.TicketService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ticket")
public class TicketController {
  private final TicketService ticketService;

  // 티켓 전체 조회
  @GetMapping("/allShow")
  public ResponseEntity<?> ticketAllShow(
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    List<TicketResponseDTO> ticketResponseDTOS = ticketService.ticketAllShow(tokenUserInfo);
    return ResponseEntity.ok().body(ticketResponseDTOS);
  }
}
