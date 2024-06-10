package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.inquiryRegisterRequestDTO;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Inquiry;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.InquiryRepository;
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
public class InquiryService {
  private final InquiryRepository inquiryRepository;
  private final UserRepository userRepository;
  private final ConcertInfoRepository concertInfoRepository;

  public boolean inquiryRegister(inquiryRegisterRequestDTO inquiryRegisterRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(inquiryRegisterRequestDTO.getConcertInfo().getId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    Inquiry entity = inquiryRegisterRequestDTO.toEntity(inquiryRegisterRequestDTO, user, concertInfo);
    inquiryRepository.save(entity);
    return true;
  }
}
