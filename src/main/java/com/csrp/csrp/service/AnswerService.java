package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.AnswerRegisterRequestDTO;
import com.csrp.csrp.dto.response.AnswerDetailResponseDTO;
import com.csrp.csrp.entity.Answer;
import com.csrp.csrp.entity.Inquiry;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.AnswerRepository;
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
public class AnswerService {
  private final UserRepository userRepository;
  private final AnswerRepository answerRepository;
  private final InquiryRepository inquiryRepository;
  // 답변 등록
  public boolean answerRegister(AnswerRegisterRequestDTO answerRegisterRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Inquiry inquiry = inquiryRepository.findById(answerRegisterRequestDTO.getInquiryId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ANSWER));
    Answer entity = answerRegisterRequestDTO.toEntity(answerRegisterRequestDTO, user, inquiry);
    answerRepository.save(entity);

    return true;
  }


  // 문의 글에 단 답변 보기
  public AnswerDetailResponseDTO answerShow(Long inquiryId, TokenUserInfo tokenUserInfo) {
    userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Inquiry inquiry = inquiryRepository.findById(inquiryId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_INQUIRY));
    Answer answer = answerRepository.findByInquiry(inquiry)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ANSWER));

    return new AnswerDetailResponseDTO(answer);
  }
}
