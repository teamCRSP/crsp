package com.csrp.csrp.form;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountForm {

  private Integer discountAmount; // 할인 가격

  private LocalDate discountStart;

  private LocalDate discountEnd;
}
