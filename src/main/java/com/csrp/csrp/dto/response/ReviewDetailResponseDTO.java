package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailResponseDTO {

  private String content;

  private int rating;

  private LocalDateTime createdAt;

  private ConcertInfo concertInfo;

  private User user;

  public ReviewDetailResponseDTO(Review review) {
    this.content = review.getContent();
    this.rating = review.getRating();
    this.createdAt = review.getCreatedAt();
    this.concertInfo = review.getConcertInfo();
    this.user = review.getUser();
  }
}
