package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.PaymentRequestDTO;
import com.csrp.csrp.dto.response.ReservationDetailResponseDTO;
import com.csrp.csrp.dto.response.TicketResponseDTO;
import com.csrp.csrp.entity.*;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.*;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TicketService {
  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final ReservationHistoryRepository reservationHistoryRepository;
  private final ReservationDetailRepository reservationDetailRepository;

  // 티켓 전체 조회
  public List<TicketResponseDTO> ticketAllShow(TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    List<Ticket> ticketList = ticketRepository.findByUser(user);

    List<TicketResponseDTO> toDto = new ArrayList<>();
    for (Ticket ticket : ticketList) {
      ReservationHistory reservationHistory = reservationHistoryRepository.findById(ticket.getReservationHistory().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
      ConcertInfo concertInfo = concertInfoRepository.findById(reservationHistory.getConcertInfo().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
      for (ReservationDetail reservationDetail : reservationDetailRepository.findByReservationHistory(reservationHistory)
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_DETAIL))) {
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(concertInfo, reservationDetail);
        toDto.add(ticketResponseDTO);
      }
    }
    return toDto;
  }

  // 티켓 발급
  public void getTicket(PaymentRequestDTO request, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReservationHistory reservationHistory = reservationHistoryRepository.findById(request.getReservationHistoryId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));

    ticketRepository.save(Ticket.builder()
        .concertDate(LocalDateTime.now())
        .user(user)
        .reservationHistory(reservationHistory)
        .build());  // 티켓 저장
  }
}
