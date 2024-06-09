package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.MyReviewPageDTO;
import com.csrp.csrp.dto.request.ReviewListResponseDTO;
import com.csrp.csrp.dto.request.ReviewRegisterRequestDTO;
import com.csrp.csrp.dto.response.MyReviewResponseDTO;
import com.csrp.csrp.dto.response.PageResponseDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.ReviewRepository;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

  // 리뷰 등록
  public boolean reviewRegister(ReviewRegisterRequestDTO reviewRegisterRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(reviewRegisterRequestDTO.getConcertInfo().getId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
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

    List<MyReviewResponseDTO> reveiwList = byUser.stream().map(MyReviewResponseDTO::new).toList();


    return ReviewListResponseDTO.builder()
        .count(reveiwList.size())
        .pageResponseDTO(new PageResponseDTO<Review>(byUser))
        .reviews(reveiwList)
        .build();
  }
}
