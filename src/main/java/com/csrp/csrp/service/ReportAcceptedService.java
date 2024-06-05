package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.ReportRegisterRequestDTO;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
    Long reviewId = reportRegisterRequestDTO.getReviewId();
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_REVIEW));
    User user = review.getUser();
    ReportAccepted reportAccepted = reportAcceptedRepository.findByUser(user);

    if (reportAccepted == null) {
      ReportAccepted entity = reportRegisterRequestDTO.toEntity(user, 1);
      reportAcceptedRepository.save(entity);
    } else {
      int warningCount = reportAccepted.getWarningCount();
      if (warningCount >= 3 && !review.isSanction()) {  // 신고 누적이 3이상이거나 제제가 아닐때
        Review build = Review.builder() // 리뷰 sanction true 변환
            .id(review.getId())
            .rating(review.getRating())
            .content(review.getContent())
            .sanction(true)
            .user(review.getUser())
            .build();
        reviewRepository.save(build);

      } else if (warningCount <= 2) { // 신고 누적이 2이하일때 누적 개수가 1증가
        ReportAccepted entity = reportRegisterRequestDTO.toModifyEntity(user, warningCount + 1, reportAccepted);
        reportAcceptedRepository.save(entity);
      }
    }
    return true;
  }

  //누적횟수가 3이고 일주일 지나면 자동 삭제
  @Async
  @Scheduled(cron = "0 0 0 * * *")   // 매일 자정에 실행
  public void AutomaticDeleteReportAccepted() {
    List<ReportAccepted> findAcceptedList = reportAcceptedRepository.findByWarningCountAndModifiedAt(3, LocalDateTime.now().minusDays(7));
    for (ReportAccepted reportAccepted : findAcceptedList) {
      reportAcceptedRepository.delete(reportAccepted);
    }
  }
}
