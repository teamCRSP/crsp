package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRegisterRequestDTO {

  @NotBlank
  @Size(min = 1, max = 100)
  private String content;

  @NotNull
  @Min(value = 1)
  @Max(value = 5)
  private int rating;

  @NotNull
  private ConcertInfo concertInfo;

  public Review ToEntity(ReviewRegisterRequestDTO reviewRegisterRequestDTO, ConcertInfo concertInfo, User user) {
    return Review.builder()
        .content(reviewRegisterRequestDTO.getContent())
        .rating(reviewRegisterRequestDTO.getRating())
        .user(user)
        .concertInfo(concertInfo)
        .build();
  }
}
