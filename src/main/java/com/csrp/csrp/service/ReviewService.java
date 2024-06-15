package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.*;
import com.csrp.csrp.dto.response.ReviewDetailResponseDTO;
import com.csrp.csrp.dto.response.PageResponseDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReportAccepted;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.ReportAcceptedRepository;
import com.csrp.csrp.repository.ReviewRepository;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import com.csrp.csrp.type.ReviewStopStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final ReportAcceptedRepository reportAcceptedRepository;
  private final PasswordEncoder encoder;

  // 리뷰 등록
  public boolean reviewRegister(ReviewRegisterRequestDTO reviewRegisterRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(reviewRegisterRequestDTO.getConcertInfoId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    ReportAccepted reportAccepted = reportAcceptedRepository.findByUser(user);
    if (reportAccepted != null && reportAccepted.getWarningCount() >= 3) {  // 해당 회원이 신고가 3번 이상 접수 됬을 경우 리뷰 등록 정지
      throw new CustomException(ErrorCode.REVIEW_STOP);
    }
    Review review = reviewRegisterRequestDTO.ToEntity(reviewRegisterRequestDTO,concertInfo, user);
    reviewRepository.save(review);
    return true;
  }

  // 내 리뷰 정보 보기
  public ReviewListResponseDTO myReview(TokenUserInfo tokenUserInfo, MyReviewPageDTO myReviewPageDTO) {
    PageRequest pageRequest = PageRequest.of(
        myReviewPageDTO.getPage() - 1,
        myReviewPageDTO.getSize(),
        Sort.by("id").descending()
    );
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Page<Review> byUser = reviewRepository.findByUser(user, pageRequest);

    List<ReviewDetailResponseDTO> reveiwList = byUser.stream().map(ReviewDetailResponseDTO::new).toList();


    return ReviewListResponseDTO.builder()
        .count(reveiwList.size())
        .pageResponseDTO(new PageResponseDTO<Review>(byUser))
        .reviews(reveiwList)
        .build();
  }

  // 전체 리뷰 보여주기
  public ReviewListResponseDTO AllReview(AllReviewPageDTO allReviewPageDTO) {
    PageRequest pageRequest = PageRequest.of(
        allReviewPageDTO.getPage() - 1,
        allReviewPageDTO.getSize(),
        Sort.by("id").descending()
    );
    Page<Review> all = reviewRepository.findAll(pageRequest);
    List<ReviewDetailResponseDTO> reviewList = all.stream().map(ReviewDetailResponseDTO::new).toList();

    return ReviewListResponseDTO.builder()
        .count(reviewList.size())
        .pageResponseDTO(new PageResponseDTO<Review>(all))
        .reviews(reviewList)
        .build();
  }

  // 리뷰 삭제
  public boolean reviewDelete(ReviewDeleteRequestDTO reviewDeleteRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    if (!user.getEmail().equals(tokenUserInfo.getEmail())) {
      throw new CustomException(ErrorCode.NOT_ACCORD_USER_EMAIL);
    }
    String inputPassword = reviewDeleteRequestDTO.getPassword();
    String encoderPassword = user.getPassword();

    if (!encoder.matches(inputPassword, encoderPassword)) {
      throw new CustomException(ErrorCode.NOT_ACCORD_USER_PASSWORD);
    }
    reviewRepository.deleteById(user.getId());
    return true;
  }

  // 리뷰 수정
  public boolean reviewModify(ReviewModifyRequestDTO reviewModifyRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Long concertId = reviewModifyRequestDTO.getConcertInfoId();
    ConcertInfo concertInfo = concertInfoRepository.findById(concertId)
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    Review review = reviewRepository.findById(reviewModifyRequestDTO.getReviewId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_REVIEW));
    Review entity = reviewModifyRequestDTO.toEntity(reviewModifyRequestDTO, review, user, concertInfo);
    if (entity.getReviewStopStatus().equals(ReviewStopStatus.YES)){
      throw new CustomException(ErrorCode.REVIEW_STOP);
    }
    reviewRepository.save(entity);

    return true;

  }
}
