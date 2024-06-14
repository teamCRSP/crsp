package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.PaymentRequestDTO;
import com.csrp.csrp.dto.response.PaymentHistoryResponseDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.PaymentHistory;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.*;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {
  private final PaymentRepository paymentRepository;
  private final UserRepository userRepository;
  private final ReservationHistoryRepository reservationHistoryRepository;
  private final ConcertInfoRepository concertInfoRepository;

  // 결제 등록
  public void paymentDone(PaymentRequestDTO request, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReservationHistory reservationHistory = reservationHistoryRepository.findById(request.getReservationHistoryId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
    PaymentHistory entity = request.toEntity(user, reservationHistory);
    paymentRepository.save(entity);

  }

  // 결제 내역 조회
  public List<PaymentHistoryResponseDTO> paymentHistoryList(TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    List<PaymentHistoryResponseDTO> toDto = new ArrayList<>();
    for (PaymentHistory paymentHistory : paymentRepository.findByUser(user)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_PAYMENT_HISTORY))) {
      ReservationHistory reservationHistory = reservationHistoryRepository.findById(paymentHistory.getReservationHistory().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
      ConcertInfo concertInfo = concertInfoRepository.findById(reservationHistory.getConcertInfo().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
      PaymentHistoryResponseDTO paymentHistoryResponseDTO = new PaymentHistoryResponseDTO(concertInfo, paymentHistory);
    toDto.add(paymentHistoryResponseDTO);
    }
    return toDto;
  }
}
