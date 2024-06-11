package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.AnswerRegisterRequestDTO;
import com.csrp.csrp.dto.response.AnswerDetailResponseDTO;
import com.csrp.csrp.service.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {
  private final AnswerService answerService;
  // 답변 등록
  @PostMapping("/register")
  public ResponseEntity<?> answerRegister(
      @Validated @RequestBody AnswerRegisterRequestDTO answerRegisterRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    boolean result = answerService.answerRegister(answerRegisterRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 문의 글에 단 답변 보기
  @GetMapping("/{inquiryId}")
  public ResponseEntity<?> answerShow(
      @PathVariable("inquiryId") Long inquiryId,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    AnswerDetailResponseDTO answerDetailResponseDTO = answerService.answerShow(inquiryId, tokenUserInfo);
    return ResponseEntity.ok().body(answerDetailResponseDTO);
  }
}
