package com.csrp.csrp.service;

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

import java.util.ArrayList;
import java.util.List;

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
      ReservationDetail reservationDetail = reservationDetailRepository.findById(ticket.getReservationDetail().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_DETAIL));
      ReservationHistory reservationHistory = reservationHistoryRepository.findById(reservationDetail.getReservationHistory().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
      ConcertInfo concertInfo = concertInfoRepository.findById(reservationHistory.getConcertInfo().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
      TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(concertInfo, reservationDetail);
      toDto.add(ticketResponseDTO);
    }

    return toDto;
  }

  // 티켓 발급
  public List<Ticket> getTicket(ReservationDetail reservationDetail, User tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    List<Ticket> ticketList = new ArrayList<>();

    Ticket save = ticketRepository.save(Ticket.builder()  // 티켓 저장
        .user(user)
        .endDate(reservationDetail.getEndDate())
        .reservationDetail(reservationDetail)
        .build());
    ticketList.add(save);

    return ticketList;
  }
}
