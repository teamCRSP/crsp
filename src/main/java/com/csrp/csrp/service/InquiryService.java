package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.InquiryDeleteRequestDTO;
import com.csrp.csrp.dto.request.InquiryModifyRequestDTO;
import com.csrp.csrp.dto.request.InquiryPageDTO;
import com.csrp.csrp.dto.request.inquiryRegisterRequestDTO;
import com.csrp.csrp.dto.response.InquiryDetailResponseDTO;
import com.csrp.csrp.dto.response.InquiryListResponseDTO;
import com.csrp.csrp.dto.response.PageResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InquiryService {
  private final InquiryRepository inquiryRepository;
  private final UserRepository userRepository;
  private final ConcertInfoRepository concertInfoRepository;
  private final PasswordEncoder encoder;

  // 문의 등록
  public boolean inquiryRegister(inquiryRegisterRequestDTO inquiryRegisterRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(inquiryRegisterRequestDTO.getConcertInfoId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    Inquiry entity = inquiryRegisterRequestDTO.toEntity(inquiryRegisterRequestDTO, user, concertInfo);
    inquiryRepository.save(entity);
    return true;
  }

  // 내 문의 리스트
  public InquiryListResponseDTO inquiryListShow(InquiryPageDTO inquiryPageDTO, TokenUserInfo tokenUserInfo) {
    PageRequest pageRequest = PageRequest.of(
        inquiryPageDTO.getPage() - 1,
        inquiryPageDTO.getSize(),
        Sort.by("id").descending()
    );
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    Page<Inquiry> byUser = inquiryRepository.findByUser(user, pageRequest);

    List<InquiryDetailResponseDTO> inquiryList = byUser.stream().map(InquiryDetailResponseDTO::new).toList();
    return InquiryListResponseDTO.builder()
        .count(inquiryList.size())
        .pageResponseDTO(new PageResponseDTO<Inquiry>(byUser))
        .inquiries(inquiryList)
        .build();
  }

  // 문의 리스트
  public InquiryListResponseDTO AllInquiry(InquiryPageDTO inquiryPageDTO) {
    PageRequest pageRequest = PageRequest.of(
        inquiryPageDTO.getPage() - 1,
        inquiryPageDTO.getSize(),
        Sort.by("id").descending()
    );

    Page<Inquiry> byUser = inquiryRepository.findAll(pageRequest);
    List<InquiryDetailResponseDTO> inquiryList = byUser.stream().map(InquiryDetailResponseDTO::new).toList();

    return InquiryListResponseDTO.builder()
        .count(inquiryList.size())
        .pageResponseDTO(new PageResponseDTO<Inquiry>(byUser))
        .inquiries(inquiryList)
        .build();
  }

  // 문의 수정
  public boolean inquiryModify(InquiryModifyRequestDTO inquiryModifyRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    ConcertInfo concertInfo = concertInfoRepository.findById(inquiryModifyRequestDTO.getConcertInfoId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    Inquiry inquiry = inquiryRepository.findById(inquiryModifyRequestDTO.getInquiryId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_INQUIRY));
    Inquiry entity = inquiryModifyRequestDTO.toEntity(inquiryModifyRequestDTO, concertInfo, inquiry, user);

    inquiryRepository.save(entity);

    return true;
  }

  // 문의 삭제
  public boolean inquiryDelete(InquiryDeleteRequestDTO inquiryDeleteRequestDTO, TokenUserInfo tokenUserInfo) {

    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    String encoderPassword = user.getPassword();
    String inputPassword = inquiryDeleteRequestDTO.getPassword();
    if (!encoder.matches(inputPassword, encoderPassword)) {
      throw new CustomException(ErrorCode.NOT_ACCORD_USER_PASSWORD);
    }
    inquiryRepository.deleteById(inquiryDeleteRequestDTO.getInquiryId());
    return true;
  }
}
