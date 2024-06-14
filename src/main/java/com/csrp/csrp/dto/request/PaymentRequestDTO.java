package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.PaymentHistory;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.PaymentStatus;
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
  private int amount;

  @NotNull
  private Long ReservationHistoryId;

  public PaymentHistory toEntity(User user, ReservationHistory reservationHistory) {
    return PaymentHistory.builder()
        .amount(this.amount)
        .paymentDate(LocalDateTime.now())
        .paymentStatus(PaymentStatus.COMPLETED)
        .user(user)
        .reservationHistory(reservationHistory)
        .build();
  }

}
