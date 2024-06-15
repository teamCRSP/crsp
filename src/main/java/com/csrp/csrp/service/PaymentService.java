package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.PaymentRequestDTO;
import com.csrp.csrp.dto.response.PaymentHistoryResponseDTO;
import com.csrp.csrp.entity.*;
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
  private final ReservationDetailRepository reservationDetailRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final ConcertLocInfoRepository concertLocInfoRepository;
  private final TicketRepository ticketRepository;
  private final ReservationHistoryService reservationHistoryService;
  private final TicketService ticketService;

  // 결제 등록
  // 결제 성공 -> 예매 내역 등록 -> 예매 내역 상세 등록 -> 티켓 발부 -> 결제 내역 등록
  public void paymentDone(List<PaymentRequestDTO> request, TokenUserInfo tokenUserInfo) {
    ReservationHistory reservationHistory = null;
    ReservationDetail reservationDetail = null;
    List<Ticket> tickets = null;
    try {
      int amount = 0;
      for (PaymentRequestDTO paymentRequestDTO : request) {
        amount += (paymentRequestDTO.getTicketCount() * paymentRequestDTO.getConcertSeatPrice());
      }
      ConcertInfo concertInfo = concertInfoRepository.findById(request.get(0).getConcertLocId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
      User user = userRepository.findById(tokenUserInfo.getId())
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
      // 예매 내역 등록
      reservationHistory = ReservationHistory.builder()
          .amount(amount)
          .user(user)
          .concertInfo(concertInfo)
          .build();
      ReservationHistory save = reservationHistoryRepository.save(reservationHistory);
      // 예매 내역 상세 등록
      for (PaymentRequestDTO paymentRequestDTO : request) {
        reservationDetail = ReservationDetail.builder()
            .endDate(paymentRequestDTO.getEndDate())
            .startDate(paymentRequestDTO.getStartDate())
            .concertSeat(paymentRequestDTO.getConcertSeat())
            .concertSeatPrice(paymentRequestDTO.getConcertSeatPrice())
            .ticketCount(paymentRequestDTO.getTicketCount())
            .concertLocInfo(concertLocInfoRepository.findById(paymentRequestDTO.getConcertLocId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND)))
            .reservationHistory(reservationHistoryRepository.findById(paymentRequestDTO.getReservationHistoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY)))
            .build();
        reservationDetailRepository.save(reservationDetail);
        // 티켓 발부
        tickets = ticketService.getTicket(paymentRequestDTO, tokenUserInfo);
      }



      // 결제 내역 등록
      paymentRepository.save(PaymentHistory.toEntity(user, reservationHistory));
    } catch (Exception e) { // 결제 실패 시 예매 내역 및 티켓 삭제
      if (reservationHistory != null) {
        reservationHistoryRepository.delete(reservationHistory);
      }
      if (tickets != null) {
          ticketRepository.deleteAll(tickets);
      }
      throw new RuntimeException(e);
    }

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