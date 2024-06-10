package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.InquiryDeleteRequestDTO;
import com.csrp.csrp.dto.request.InquiryModifyRequestDTO;
import com.csrp.csrp.dto.request.InquiryPageDTO;
import com.csrp.csrp.dto.request.inquiryRegisterRequestDTO;
import com.csrp.csrp.dto.response.InquiryListResponseDTO;
import com.csrp.csrp.service.InquiryService;
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

  // 내 문의 리스트
  @GetMapping("/myInquiry")
  public ResponseEntity<?> inquiryListShow(
      @Validated @RequestBody InquiryPageDTO inquiryPageDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    InquiryListResponseDTO inquiryListResponseDTO = inquiryService.inquiryListShow(inquiryPageDTO, tokenUserInfo);
    return ResponseEntity.ok().body(inquiryListResponseDTO);
  }

  // 문의 리스트
  @GetMapping("/inquiryList")
  public ResponseEntity<?> AllInquiry(
      @Validated @RequestBody InquiryPageDTO inquiryPageDTO
  ) {
    InquiryListResponseDTO inquiryListResponseDTO = inquiryService.AllInquiry(inquiryPageDTO);
    return ResponseEntity.ok().body(inquiryListResponseDTO);
  }

  // 문의 수정하기
  @RequestMapping(value = "/modify", method = {RequestMethod.PUT, RequestMethod.PATCH})
  public ResponseEntity<?> inquiryModify(
      @Validated @RequestBody InquiryModifyRequestDTO inquiryModifyRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    boolean result = inquiryService.inquiryModify(inquiryModifyRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 문의 삭제
  @DeleteMapping("/delete")
  public ResponseEntity<?> inquiryDelete(
      @Validated @RequestBody InquiryDeleteRequestDTO inquiryDeleteRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    boolean result = inquiryService.inquiryDelete(inquiryDeleteRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }
}
