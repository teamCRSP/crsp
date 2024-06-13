package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationDetail;
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

  private String seatInfo;

  private int ticketCount;

  private String concertDate;

  private String concertLocation;

  private int concertSeatPrice;

  private Long concertInfoId;

  public ReservationDetailResponseDTO(ReservationDetail reservationDetail, ConcertInfo concertInfo) {
    this.title = concertInfo.getTitle();
    this.concertImage = concertInfo.getConcertImage();
    this.concertType = concertInfo.getConcertType();
    this.seatInfo = reservationDetail.getConcertSeat();
    this.ticketCount = reservationDetail.getTicketCount();
    this.concertDate = reservationDetail.getConcertDate();
    this.concertSeatPrice = reservationDetail.getConcertSeatPrice();
    this.concertLocation = reservationDetail.getConcertLocInfo().getLocation().get(0);  /////////////////////////// 고치기
    this.concertInfoId = concertInfo.getId();
  }

}
