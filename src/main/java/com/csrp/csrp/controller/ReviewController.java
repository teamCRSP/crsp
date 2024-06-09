package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.MyReviewPageDTO;
import com.csrp.csrp.dto.request.ReviewListResponseDTO;
import com.csrp.csrp.dto.request.ReviewRegisterRequestDTO;
import com.csrp.csrp.service.ReviewService;
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
@RequestMapping("/review")
public class ReviewController {
  private final ReviewService reviewService;

  // 리뷰 등록
  @PostMapping("/register")
  public ResponseEntity<?> reviewRegister(@Validated @RequestBody ReviewRegisterRequestDTO reviewRegisterRequestDTO,
                                          @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    boolean result = reviewService.reviewRegister(reviewRegisterRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 내 리뷰 정보 보기
  @GetMapping("/myReview")
  public ResponseEntity<?> myReview(@Validated @RequestBody MyReviewPageDTO myReviewPageDTO,
                                    @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    ReviewListResponseDTO myReviewResponseDTO = reviewService.myReview(tokenUserInfo, myReviewPageDTO);
    return ResponseEntity.ok().body(myReviewResponseDTO);
  }
}
