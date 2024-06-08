package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportShowResponseDTO {
  private int warningCount;
  private User user;
  private List<Review> reviewList;

}
