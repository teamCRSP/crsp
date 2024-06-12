package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ReservationHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationListResponseDTO {
  private List<ReservationHistory> reservationHistories;

}
