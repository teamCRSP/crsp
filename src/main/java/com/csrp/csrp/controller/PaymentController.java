package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.PaymentRequestDTO;
import com.csrp.csrp.dto.response.PaymentHistoryResponseDTO;
import com.csrp.csrp.service.PaymentService;
import com.csrp.csrp.service.ReservationHistoryService;
import com.csrp.csrp.service.TicketService;
import com.csrp.csrp.token.TokenUserInfo;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
  private final PaymentService paymentService;
  private final TicketService ticketService;
  private IamportClient iamportClient;

  @Value("${IMP_API_KEY}")
  private String apiKey;

  @Value("${imp.api.secretKey}")
  private String secretKey;

  @PostConstruct
  public void init() {
    this.iamportClient = new IamportClient(apiKey, secretKey);
  }

  // 결제 완료
  @PostMapping("/{imp_uid}")
  public IamportResponse<Payment> validateIamPort(@PathVariable String imp_uid, @Validated @RequestBody PaymentRequestDTO request,
                                                  @AuthenticationPrincipal TokenUserInfo tokenUserInfo) throws IamportResponseException, IOException {

    IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);

    log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());

    paymentService.paymentDone(request, tokenUserInfo);
    ticketService.getTicket(request, tokenUserInfo);
    return payment;
  }

  // 결제 내역 조회
  @GetMapping("/history")
  public ResponseEntity<List<PaymentHistoryResponseDTO>> paymentList(@AuthenticationPrincipal TokenUserInfo tokenUserInfo) {
    List<PaymentHistoryResponseDTO> paymentHistoryResponseDTOS = paymentService.paymentHistoryList(tokenUserInfo);
    return ResponseEntity.ok().body(paymentHistoryResponseDTOS);
  }
}