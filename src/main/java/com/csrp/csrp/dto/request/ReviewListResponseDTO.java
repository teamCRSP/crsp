package com.csrp.csrp.dto.request;

import com.csrp.csrp.dto.response.ReviewDetailResponseDTO;
import com.csrp.csrp.dto.response.PageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponseDTO {
  private int count;
  private PageResponseDTO pageResponseDTO;
  private List<ReviewDetailResponseDTO> reviews;
}
