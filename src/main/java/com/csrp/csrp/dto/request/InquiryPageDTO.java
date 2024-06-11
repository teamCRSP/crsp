package com.csrp.csrp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class InquiryPageDTO {

  @NotNull
  private int page;

  @NotNull
  private int size;

  public InquiryPageDTO() {
    this.page = 1;
    this.size = 15;
  }
}
