package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRegisterRequestDTO {

  @NotNull
  private Long concertId;

  @NotNull
  private String seatInfo;

  @NotNull
  @Min(1)
  private int count;  // 개수

  @NotNull
  private String concertDate;

  @NotNull
  private String concertLocation;

  @NotNull
  private int amount;

  public ReservationHistory toEntity(User user, ConcertInfo concertInfo, ReservationRegisterRequestDTO reservationRegisterRequestDTO) {
    return ReservationHistory.builder()
        .seatInfo(reservationRegisterRequestDTO.getSeatInfo())
        .count(reservationRegisterRequestDTO.getCount())
        .concertDate(reservationRegisterRequestDTO.getConcertDate())
        .concertLocation(reservationRegisterRequestDTO.getConcertLocation())
        .amount(reservationRegisterRequestDTO.getAmount())
        .user(user)
        .concertInfo(concertInfo)
        .build();
  }
}
