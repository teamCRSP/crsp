package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.LoveRequestDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Love;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.LoveRepository;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoveService {
  private final UserRepository userRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final LoveRepository loveRepository;

  // 좋아요 클릭
  public boolean loveClick(LoveRequestDTO loveRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(loveRequestDTO.getConcertInfoId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    Love existsLove = loveRepository.findByUserAndConcertInfo(user, concertInfo);

    if (existsLove == null) { // 좋아요 생성
      Love entity = loveRequestDTO.toEntity(user, concertInfo);
      loveRepository.save(entity);
      return true;
    } else {  // 좋아요 삭제
      loveRepository.delete(existsLove);
      return false;
    }
  }

  public int loveCount(Long concertInfoId) {
    ConcertInfo concertInfo = concertInfoRepository.findById(concertInfoId)
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    return loveRepository.countByConcertInfo(concertInfo);
  }
}
