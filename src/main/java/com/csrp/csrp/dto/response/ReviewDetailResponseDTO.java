package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.Review;
import com.csrp.csrp.type.ConcertType;
import com.csrp.csrp.type.ReviewStopStatus;
import com.csrp.csrp.type.Role;
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

  private String createdAt;

  private String title;

  private String concertImage;

  private ConcertType concertType;

  private String name;

  private int age;

  private String profileImage;

  private Role role;

  private boolean sanction;

  private ReviewStopStatus reviewStopStatus;

  public ReviewDetailResponseDTO(Review review) {
    this.content = review.getContent();
    this.rating = review.getRating();
    this.createdAt = String.valueOf(review.getCreatedAt());
    this.title = review.getConcertInfo().getTitle();
    this.concertType = review.getConcertInfo().getConcertType();
    this.concertImage = review.getConcertInfo().getConcertImage();
    this.name = review.getUser().getName();
    this.age = review.getUser().getAge();
    this.profileImage = review.getUser().getProfileImage();
    this.role = review.getUser().getRole();
    this.sanction = review.isSanction();
    this.reviewStopStatus = review.getReviewStopStatus();
  }
}
