package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.*;
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

  // 전체 리뷰 정보 보기
  @GetMapping("/reviewList")
  public ResponseEntity<?> AllReview(@Validated @RequestBody AllReviewPageDTO allReviewPageDTO) {
    ReviewListResponseDTO reviewListResponseDTO = reviewService.AllReview(allReviewPageDTO);
    return ResponseEntity.ok().body(reviewListResponseDTO);
  }

  // 리뷰 삭제
  @DeleteMapping("/delete")
  public ResponseEntity<?> reviewDelete(@Validated @RequestBody ReviewDeleteRequestDTO reviewDeleteRequestDTO,
                                        @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    boolean result = reviewService.reviewDelete(reviewDeleteRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 리뷰 수정
  @RequestMapping(value = "/modify", method = {RequestMethod.PATCH, RequestMethod.PUT})
  public ResponseEntity<?> reviewModify(@Validated @RequestBody ReviewModifyRequestDTO reviewModifyRequestDTO,
                                        @AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    boolean result = reviewService.reviewModify(reviewModifyRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }
}
