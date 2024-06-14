package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationDetail;
import com.csrp.csrp.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDTO {

  private LocalDateTime concertDate;

  private String concertImage;

  private String title;

  private String concertSeat;

  private int ticketCount;

  public TicketResponseDTO(ConcertInfo concertInfo, ReservationDetail reservationDetail) {
    this.concertDate = reservationDetail.getConcertDate();
    this.concertImage = concertInfo.getConcertImage();
    this.title = concertInfo.getTitle();
    this.concertSeat = reservationDetail.getConcertSeat();
    this.ticketCount = reservationDetail.getTicketCount();
  }
}
