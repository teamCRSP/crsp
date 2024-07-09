package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.ReservationPageDTO;
import com.csrp.csrp.dto.response.PageResponseDTO;
import com.csrp.csrp.dto.response.ReservationHistoryListResponseDTO;
import com.csrp.csrp.dto.response.ReservationHistoryResponseDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ReservationHistoryRepository;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
  public ReservationHistoryListResponseDTO reservationHistoryShow(TokenUserInfo tokenUserInfo, ReservationPageDTO reservationPageDTO) {
    PageRequest pageRequest = PageRequest.of(
        reservationPageDTO.getPage() - 1,
        reservationPageDTO.getSize(),
        Sort.by("id").descending()
    );

    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Page<ReservationHistory> byUser = reservationHistoryRepository.findByUser(user, pageRequest);
    List<ReservationHistoryResponseDTO> reservationHistoryResponseDTOList = byUser.stream().map(ReservationHistoryResponseDTO::new).toList();

    return ReservationHistoryListResponseDTO.builder()
        .count(reservationHistoryResponseDTOList.size())
        .pageResponseDTO(new PageResponseDTO<ReservationHistory>(byUser))
        .reservationHistories(reservationHistoryResponseDTOList)
        .build();
  }


}
