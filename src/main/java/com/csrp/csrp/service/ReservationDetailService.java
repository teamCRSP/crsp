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

  // 예매 내역 등록 및 상세 등록
  public boolean reservationDetailRegister(List<ReservationDetailRegisterRequestDTO> reservationDetailRegisterRequestDTOList, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    int amount = 0;
    for (ReservationDetailRegisterRequestDTO reservationDetailRegisterRequestDTO : reservationDetailRegisterRequestDTOList) {
      amount += (reservationDetailRegisterRequestDTO.getConcertSeatPrice() * reservationDetailRegisterRequestDTO.getTicketCount());  // 좌석 가격 총합
    }
    ConcertLocInfo concertLoc = concertLocInfoRepository.findById(reservationDetailRegisterRequestDTOList.get(0).getConcertLocId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND));
    ConcertInfo concert = concertInfoRepository.findById(concertLoc.getConcertInfo().getId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    ReservationHistory reservationHistory = reservationHistoryService.reservationHistoryRegister(user, concert, amount); // 예매 내역 저장

    for (ReservationDetailRegisterRequestDTO reservationDetailRegisterRequestDTO : reservationDetailRegisterRequestDTOList) {
      ConcertLocInfo concertLocInfo = concertLocInfoRepository.findById(reservationDetailRegisterRequestDTO.getConcertLocId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND));

      ReservationDetail entity = reservationDetailRegisterRequestDTO.toEntity(reservationHistory, concertLocInfo, reservationDetailRegisterRequestDTO);
     reservationDetailRepository.save(entity);  // 예매 내역 상세 저장
    }

    return true;
  }


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
    List<ReservationDetail> reservationDetailList = reservationDetailRepository.findByReservationHistory(history);
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
