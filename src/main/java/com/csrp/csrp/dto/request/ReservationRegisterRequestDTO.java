package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
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
  private int amount;

  public ReservationHistory toEntity(User user, ConcertInfo concertInfo, ReservationRegisterRequestDTO reservationRegisterRequestDTO) {
    return ReservationHistory.builder()
        .amount(reservationRegisterRequestDTO.getAmount())
        .user(user)
        .concertInfo(concertInfo)
        .build();
  }
}
