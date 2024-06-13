package com.csrp.csrp.service;

import com.csrp.csrp.dto.response.ReservationHistoryResponseDTO;
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
  private final ReservationHistoryRepository reservationHistoryRepository;
  private final UserRepository userRepository;

  // 예매 내역 등록
  public ReservationHistory reservationHistoryRegister(User user, ConcertInfo concertInfo, int amount) {
    ReservationHistory reservationHistory = ReservationHistory.builder()
        .amount(amount)
        .user(user)
        .concertInfo(concertInfo)
        .build();
    return reservationHistoryRepository.save(reservationHistory);
  }


  // 예매 내역 조회
  public List<ReservationHistoryResponseDTO> reservationHistoryShow(TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    List<ReservationHistory> reservationHistory = reservationHistoryRepository.findByUser(user);
    return reservationHistory.stream().map(ReservationHistoryResponseDTO::new).toList();
  }

  // 예매 내역 삭제
  public boolean reservationHistoryDelete(Long reservationHistoryDeleteRequestDTO, TokenUserInfo tokenUserInfo) {
    userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ReservationHistory reservationHistory = reservationHistoryRepository.findById(reservationHistoryDeleteRequestDTO)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_RESERVATION_HISTORY));
    reservationHistoryRepository.delete(reservationHistory);
    return true;
  }



}
