package com.csrp.csrp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistoryListResponseDTO {
  private int count;
  private PageResponseDTO pageResponseDTO;
  private List<PaymentHistoryResponseDTO> paymentHistories;
}
