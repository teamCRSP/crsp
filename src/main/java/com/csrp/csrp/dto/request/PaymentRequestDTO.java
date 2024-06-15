package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.PaymentHistory;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.PaymentStatus;
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
public class PaymentRequestDTO {

  @NotNull
  private Long concertLocInfoId;

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
  private LocalDateTime startDate;

  @NotNull
  private LocalDateTime endDate;

  @NotNull
  private Long reservationHistoryId;


}
