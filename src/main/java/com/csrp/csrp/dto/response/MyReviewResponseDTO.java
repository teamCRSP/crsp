package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyReviewResponseDTO {

  private String content;

  private int rating;

  private ConcertInfo concertInfo;

  private User user;

  public MyReviewResponseDTO(Review review) {
    this.content = review.getContent();
    this.rating = review.getRating();
    this.concertInfo = review.getConcertInfo();
    this.user = review.getUser();
  }
}
