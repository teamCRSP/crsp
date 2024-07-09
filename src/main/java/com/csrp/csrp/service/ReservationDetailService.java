package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.ReservationDetailRegisterRequestDTO;
import com.csrp.csrp.dto.response.ReservationDetailResponseDTO;
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
public class ReservationDetailService {
  private final UserRepository userRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final ReservationHistoryRepository reservationHistoryRepository;
  private final ConcertLocInfoRepository concertLocInfoRepository;
  private final ReservationHistoryService reservationHistoryService;
  private final ReservationDetailRepository reservationDetailRepository;


  // 예매 내역 상세 보기
  public ReservationDetailResponseDTO myReservationDetail(Long reservationDetailId, TokenUserInfo tokenUserInfo) {
    userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReservationDetail reservationDetail = reservationDetailRepository.findById(reservationDetailId
        )
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_TICKET));
    ReservationHistory reservationHistory = reservationHistoryRepository.findById(reservationDetail.getReservationHistory().getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
    return new ReservationDetailResponseDTO(reservationDetail, reservationHistory.getConcertInfo());
  }

  // 예매 내역 상세 리스트 보기
  public List<ReservationDetailResponseDTO> reservationDetailList(Long reservationHistoryId, TokenUserInfo tokenUserInfo) {
    userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReservationHistory history = reservationHistoryRepository.findById(reservationHistoryId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
    List<ReservationDetail> reservationDetailList = reservationDetailRepository.findByReservationHistory(history)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_DETAIL));
    List<ReservationDetailResponseDTO> toDto = new ArrayList<>();
    for (ReservationDetail reservationDetail : reservationDetailList) {
      ReservationHistory reservationHistory = reservationHistoryRepository.findById(reservationDetail.getReservationHistory().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
      ReservationDetailResponseDTO reservationDetailResponseDTO = new ReservationDetailResponseDTO(reservationDetail, reservationHistory.getConcertInfo());
      toDto.add(reservationDetailResponseDTO);
    }
    return toDto;
  }

}
