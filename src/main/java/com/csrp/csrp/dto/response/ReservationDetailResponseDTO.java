package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationDetail;
import com.csrp.csrp.type.ConcertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String concertLocation;

  private int concertSeatPrice;

  private Long concertInfoId;

  public ReservationDetailResponseDTO(ReservationDetail reservationDetail, ConcertInfo concertInfo) {
    this.title = concertInfo.getTitle();
    this.concertImage = concertInfo.getConcertImage();
    this.concertType = concertInfo.getConcertType();
    this.seatInfo = reservationDetail.getConcertSeat();
    this.ticketCount = reservationDetail.getTicketCount();
    this.startDate = reservationDetail.getStartDate();
    this.endDate = reservationDetail.getEndDate();
    this.concertSeatPrice = reservationDetail.getConcertSeatPrice();
    this.concertLocation = reservationDetail.getConcertLocInfo().getLocation();
    this.concertInfoId = concertInfo.getId();
  }

}
