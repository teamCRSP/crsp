package com.csrp.csrp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservationPageDTO {
  @NotNull
  private int page;

  @NotNull
  private int size;

  public ReservationPageDTO() {
    this.page = 1;
    this.size = 15;
  }
}
