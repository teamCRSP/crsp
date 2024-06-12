package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.type.ConcertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDetailResponseDTO {

  private String title;

  private String concertImage;

  private ConcertType concertType;

  private int amount;

  public ReservationDetailResponseDTO(ReservationHistory reservationHistory) {
    this.title = reservationHistory.getConcertInfo().getTitle();
    this.concertImage = reservationHistory.getConcertInfo().getConcertImage();
    this.concertType = reservationHistory.getConcertInfo().getConcertType();
    this.amount = reservationHistory.getAmount();
  }
}
