package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.ReportRegisterRequestDTO;
import com.csrp.csrp.dto.response.ReportShowResponseDTO;
import com.csrp.csrp.service.ReportAcceptedService;
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
@RequestMapping("/reportAccepted")
public class ReportAcceptedController {
  private final ReportAcceptedService reportAcceptedService;

  // 신고
  @PostMapping("/register")
  public ResponseEntity<?> reportRegister(
      @RequestBody @Validated ReportRegisterRequestDTO reportRegisterRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    boolean result = reportAcceptedService.reportRegister(reportRegisterRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 신고 누적횟수 보기
  @GetMapping("show")
  public ResponseEntity<?> reportShow(
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    ReportShowResponseDTO reportShowResponseDTO = reportAcceptedService.reportShow(tokenUserInfo);
    return ResponseEntity.ok().body(reportShowResponseDTO);
  }

}
