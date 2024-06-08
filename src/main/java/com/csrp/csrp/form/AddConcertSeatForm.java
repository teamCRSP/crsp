package com.csrp.csrp.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddConcertSeatForm {

  private Integer seatS;
  private Integer seatSPrice;

  private Integer seatA;
  private Integer seatAPrice;

  private Integer seatB;
  private Integer seatBPrice;
}
