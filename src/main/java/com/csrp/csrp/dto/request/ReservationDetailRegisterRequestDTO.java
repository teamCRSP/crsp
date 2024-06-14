package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDetailRegisterRequestDTO {

  @NotNull
  private Long concertLocId;

  @NotNull
  private String concertSeat;

  @NotNull
  private int concertSeatPrice;

  @NotNull
  @Min(1)
  private int ticketCount;  // 개수

  @NotNull
  private LocalDateTime concertDate;

  public ReservationDetail toEntity(ReservationHistory reservationHistory, ConcertLocInfo concertLocInfo, ReservationDetailRegisterRequestDTO reservationRegisterRequestDTO) {
    return ReservationDetail.builder()
        .concertSeat(reservationRegisterRequestDTO.getConcertSeat())
        .ticketCount(reservationRegisterRequestDTO.getTicketCount())
        .concertDate(reservationRegisterRequestDTO.getConcertDate())
        .concertSeatPrice(reservationRegisterRequestDTO.getConcertSeatPrice())
        .ticketingDate(LocalDateTime.now())
        .concertLocInfo(concertLocInfo)
        .reservationHistory(reservationHistory)
        .build();
  }
}
