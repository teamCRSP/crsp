package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModifyRequestDTO {

  @NotNull
  private Long reviewId;

  @NotBlank
  @Size(min = 1, max = 100)
  private String content;

  @NotNull
  @Min(value = 1)
  @Max(value = 5)
  private int rating;

  @NotNull
  private Long concertInfoId;

  public Review toEntity(ReviewModifyRequestDTO reviewModifyRequestDTO, Review review, User user, ConcertInfo concertInfo) {
    return Review.builder()
        .id(reviewModifyRequestDTO.getReviewId())
        .content(reviewModifyRequestDTO.getContent())
        .rating(reviewModifyRequestDTO.getRating())
        .sanction(review.isSanction())
        .reviewWarningCount(review.getReviewWarningCount())
        .user(user)
        .concertInfo(concertInfo)
        .build();
  }

}
