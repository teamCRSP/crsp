package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.PaymentHistory;
import com.csrp.csrp.type.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistoryResponseDTO {
  private String title;

  private int amount;

  private LocalDateTime paymentDate;

  private PaymentStatus paymentStatus;

  public PaymentHistoryResponseDTO(ConcertInfo concertInfo, PaymentHistory paymentHistory) {
    this.title = concertInfo.getTitle();
    this.amount = paymentHistory.getAmount();
    this.paymentDate = paymentHistory.getPaymentDate();
    this.paymentStatus = paymentHistory.getPaymentStatus();
  }
}
