package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.inquiryRegisterRequestDTO;
import com.csrp.csrp.repository.InquiryRepository;
import com.csrp.csrp.service.InquiryService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/inquiry")
public class InquiryController {
  private final InquiryService inquiryService;

  // 문의 등록
  @PostMapping("/register")
  public ResponseEntity<?> inquiryRegister(
      @Validated @RequestBody inquiryRegisterRequestDTO inquiryRegisterRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    boolean result = inquiryService.inquiryRegister(inquiryRegisterRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }


}
