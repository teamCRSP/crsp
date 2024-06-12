package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.ReservationRegisterRequestDTO;
import com.csrp.csrp.dto.response.ReservationDetailResponseDTO;
import com.csrp.csrp.dto.response.ReservationListResponseDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.ReservationHistoryRepository;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationHistoryService {
  private final UserRepository userRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final ReservationHistoryRepository reservationHistoryRepository;

  // 예매 내역 등록
  public boolean ReservationRegister(ReservationRegisterRequestDTO reservationRegisterRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(reservationRegisterRequestDTO.getConcertId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    ReservationHistory entity = reservationRegisterRequestDTO.toEntity(user, concertInfo, reservationRegisterRequestDTO);
    reservationHistoryRepository.save(entity);
    return true;
  }


  // 예매 내역 상세 보기
  public ReservationDetailResponseDTO myReservationDetail(Long reservationHistoryId, TokenUserInfo tokenUserInfo) {
    userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReservationHistory reservationHistory = reservationHistoryRepository.findById(reservationHistoryId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));

    return new ReservationDetailResponseDTO(reservationHistory);
  }

  // 예매 내역 리스트 보기
  public ReservationListResponseDTO myReservationList(TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    List<ReservationHistory> historyList = reservationHistoryRepository.findByUser(user);
    return new ReservationListResponseDTO(historyList);
  }
}
