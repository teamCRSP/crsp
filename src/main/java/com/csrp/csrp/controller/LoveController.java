package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.LoveRequestDTO;
import com.csrp.csrp.service.LoveService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/love")
public class LoveController {
  private final LoveService loveService;

  // 좋아요 클릭
  @PostMapping("/click")
  public ResponseEntity<?> loveClick(
      @Validated @RequestBody LoveRequestDTO loveRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    boolean result = loveService.loveClick(loveRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 좋아요 개수 보기
  @GetMapping("/{concertInfoId}")
  public ResponseEntity<?> loveCount(
      @PathVariable("concertInfoId") Long concertInfoId
  ) {
    int loveCount = loveService.loveCount(concertInfoId);
    return ResponseEntity.ok().body(loveCount);
  }

}
