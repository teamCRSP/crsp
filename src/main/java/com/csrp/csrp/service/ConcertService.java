package com.csrp.csrp.service;

import com.csrp.csrp.entity.User;
import com.csrp.csrp.form.EachConcertInfoForm;
import com.csrp.csrp.form.EachConcertUpdateForm;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;

import java.time.LocalDateTime;
import java.util.*;

import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.form.ConcertUpdateForm;
import org.springframework.web.multipart.MultipartFile;
import com.csrp.csrp.entity.EachConcertInfo;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ConcertLocInfo;
import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.repository.EachConcertInfoRepository;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.ConcertLocInfoRepository;
import com.csrp.csrp.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

  private final ConcertInfoRepository concertInfoRepository;
  private final EachConcertInfoRepository eachConcertInfoRepository;
  private final ConcertLocInfoRepository concertLocRepository;
  private final ConcertLocInfoRepository concertLocInfoRepository;
  private final UserRepository userRepository;

  // 파일명 변경 (사진 이름 중복 방지)
  public String uploadProfileImage(MultipartFile profileImage) {
    String profilePath = null;
    if (profileImage != null) {
      profilePath = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
    }
    return profilePath;
  }

  @Transactional
  public boolean registerConcert(ConcertForm form, TokenUserInfo tokenAccountUserInfo, MultipartFile profileImage) {


    String concertImagePath = uploadProfileImage(profileImage);

    Long id = tokenAccountUserInfo.getId();

    User user = userRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));

    ConcertInfo concertInfo = ConcertInfo.from(form,concertImagePath, user);

    concertInfoRepository.save(concertInfo);

    for (String location : form.getLocation()) {
      ConcertLocInfo concertLoc = ConcertLocInfo.from(form, location, concertInfo);
      concertLocRepository.save(concertLoc);

    }

    return true;
  }


  @Transactional
  public void registerEachConcert(String concertName, String concertLocation, EachConcertInfoForm form) {

    if(form.getEndDate().isBefore(form.getStartDate())){
      throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
    }

    if(!concertInfoRepository.existsByTitle(concertName) && !concertLocInfoRepository.existsByLocation(concertLocation)){
      throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
    }

    EachConcertInfo eachConcert = eachConcertInfoRepository.findByConcertNameAndConcertLocation(
        concertName, concertLocation).orElseThrow(()->new CustomException(ErrorCode.CONCERT_NOT_FOUND));

    LocalDateTime startDate = form.getStartDate();
    LocalDateTime endDate = form.getEndDate();

    // 전체 콘서트 일정안에 각각의 개최하는 장소가 다른 콘서트들은 전체 일정 안에 있어야 한다
    if(startDate.isBefore(eachConcert.getConcertStartDate()) && endDate.isAfter(eachConcert.getConcertEndDate())){
      throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
    }

    List<ConcertLocInfo> concertLocInfoList = concertLocInfoRepository.findAllByConcertTitle(concertName);



    for (ConcertLocInfo concertLocInfo : concertLocInfoList){
      if(concertLocation.equals(concertLocInfo.getLocation())){
        // 특정 콘서트에 대한 콘서트 위치

        EachConcertInfo eachConcertInfo = EachConcertInfo.from(form, concertName, concertLocation, concertLocInfo);

        eachConcertInfoRepository.save(eachConcertInfo);
      }
    }

  }

  @Transactional
  public boolean updateConcert(ConcertUpdateForm form, MultipartFile profileImage) {

    String concertImagePath = uploadProfileImage(profileImage);


    // 존재하지 않는 콘서트에 대해 업데이트 시도하는 경우
    if(concertInfoRepository.findById(form.getConcertInfoId()).isEmpty()) {
      throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
    }

    // 콘서트 정보 업데이트

    ConcertInfo concertInfo = concertInfoRepository.findById(form.getConcertInfoId())
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

    Integer likeCount = concertInfoRepository.findById(form.getConcertInfoId()).get().getLikeCount();

    Integer reviewCount = concertInfoRepository.findById(form.getConcertInfoId()).get().getReviewCount();

    concertInfo.setTitle(form.getTitle());
    concertInfo.setArtist(form.getArtist());
    concertInfo.setDescription(form.getDescription());
    concertInfo.setLikeCount(likeCount);
    concertInfo.setStartDate(form.getStartDate());
    concertInfo.setEndDate(form.getEndDate());
    concertInfo.setSeatSPrice(form.getSeatSPrice());
    concertInfo.setSeatAPrice(form.getSeatAPrice());
    concertInfo.setSeatBPrice(form.getSeatBPrice());
    concertInfo.setReviewCount(reviewCount);
    concertInfo.setConcertImage(concertImagePath);
    concertInfo.setConcertType(form.getConcertType());

    concertInfoRepository.save(concertInfo);

    return true;
  }

  @Transactional
  public Boolean updateEachConcert(String concertName, String concertLocation, EachConcertUpdateForm form) {
    if(form.getEndDate().isBefore(form.getStartDate())){
      throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
    }

    EachConcertInfo eachConcertInfo = eachConcertInfoRepository.findByConcertNameAndConcertLocation(
        concertName, concertLocation).orElseThrow(()->new CustomException(ErrorCode.CONCERT_NOT_FOUND));

    String findLocation = eachConcertInfo.getConcertLocation();

    ConcertLocInfo concertLocInfo = concertLocInfoRepository.findByLocation(findLocation);


    eachConcertInfo.setConcertName(concertName);
    eachConcertInfo.setConcertLocInfo(concertLocInfo);
    eachConcertInfo.setConcertLocation(concertLocation);
    eachConcertInfo.setConcertStartDate(form.getStartDate());
    eachConcertInfo.setConcertEndDate(form.getEndDate());
    eachConcertInfo.setSeatS(form.getSeatS());
    eachConcertInfo.setSeatA(form.getSeatA());
    eachConcertInfo.setSeatB(form.getSeatB());

    eachConcertInfoRepository.save(eachConcertInfo);

    return true;
  }
}
