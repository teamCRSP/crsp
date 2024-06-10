package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.ReportRegisterRequestDTO;
import com.csrp.csrp.dto.response.ReportShowResponseDTO;
import com.csrp.csrp.entity.ReportAccepted;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ReportAcceptedRepository;
import com.csrp.csrp.repository.ReviewRepository;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportAcceptedService {
  private final ReportAcceptedRepository reportAcceptedRepository;
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  // 신고
  public boolean reportRegister(ReportRegisterRequestDTO reportRegisterRequestDTO, TokenUserInfo tokenUserInfo) {

    userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Review review = reviewRepository.findById(reportRegisterRequestDTO.getReviewId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_REVIEW));
    User user = review.getUser();
    ReportAccepted reportAccepted = reportAcceptedRepository.findByUser(user);

    Review addReviewWarningCount = reportRegisterRequestDTO.addReviewWarningCount(review);  // 리뷰 신고누적횟수 1 증가
    Review save = reviewRepository.save(addReviewWarningCount);

    if (!save.isSanction() && save.getReviewWarningCount() >= 3) {
      Review build = reportRegisterRequestDTO.toReview(save);     // 리뷰 제제를 true 변환
      reviewRepository.save(build);
    }

    int findReviewCount = reviewRepository.countByUserAndSanctionIsTrue(user);// 해당 회원에 대한 제제 값이 true인 데이터의 개수
    if (reportAccepted == null && findReviewCount >= 1) {  // 리뷰 제제가 true인 데이터 개수 1이상 그리고 신고 접수가 없을때
      ReportAccepted entity = reportRegisterRequestDTO.toEntity(user, findReviewCount);
      entity.setCompareDate(null);
      reportAcceptedRepository.save(entity);
    } else if (reportAccepted != null && findReviewCount >=1){
        ReportAccepted entity = reportRegisterRequestDTO.toModifyEntity(user, findReviewCount, reportAccepted);
        if (entity.getWarningCount() == 3) {  // 신고누적횟수가 3일때 비교 날짜 생성
          entity.setCompareDate(LocalDateTime.now());   // 스캐줄러를 이용하기 위한 비교 날짜
        }
        reportAcceptedRepository.save(entity);
    }
    return true;
  }

  // 신고 누적횟수 보기
  public ReportShowResponseDTO reportShow(TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReportAccepted accepted = reportAcceptedRepository.findByUser(user);
    List<Review> reviewList = reviewRepository.findByUserAndSanction(user, true); // 해당 회원의 제제 된 리뷰 리스트
    if (accepted == null) {
      return new ReportShowResponseDTO(0, user, reviewList);
    }
    int warningCount = accepted.getWarningCount();

    return new ReportShowResponseDTO(warningCount, user, reviewList);
  }
}
