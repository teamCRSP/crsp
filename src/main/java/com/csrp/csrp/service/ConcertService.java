package com.csrp.csrp.service;

import java.time.LocalDateTime;
import java.util.*;

import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.form.ConcertUpdateForm;
import org.springframework.web.multipart.MultipartFile;
import com.csrp.csrp.entity.ConcertDateInfo;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ConcertLocInfo;
import com.csrp.csrp.entity.ConcertSeatInfo;
import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.repository.ConcertDateInfoRepository;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.ConcertLocInfoRepository;
import com.csrp.csrp.repository.ConcertSeatInfoRepository;
import com.csrp.csrp.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

  private final ConcertInfoRepository concertInfoRepository;

  private final ConcertDateInfoRepository concertDateRepository;

  private final ConcertLocInfoRepository concertLocRepository;

  private final ConcertSeatInfoRepository concertSeatRepository;
  private final ConcertLocInfoRepository concertLocInfoRepository;

  // 파일명 변경 (사진 이름 중복 방지)
  public String uploadProfileImage(MultipartFile profileImage) {
    String profilePath = null;
    if (profileImage != null) {
      profilePath = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
    }
    return profilePath;
  }

  @Transactional
  public boolean registerConcert(ConcertForm form, MultipartFile profileImage) {

    // 등록할 콘서트 중복 체크
    // 같은 콘서트라면 제목, 장소, 개최 일시(예: 2024-06-08T12:30)가 같다
//    if (concertInfoRepository.existsByTitle(form.getTitle()) && concertLocRepository.existsByLocation(form.getLocation()) && concertDateRepository.existsByConcertDate(form.getDate())){
//      throw new ConcertException(ErrorCode.CONCERT_ALREADY_EXISTS);
//    }

    if(form.getEndDate().isBefore(form.getStartDate())){
      throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
    }

    String concertImagePath = uploadProfileImage(profileImage);

    ConcertInfo concertInfo = ConcertInfo.from(form,concertImagePath);

    concertInfoRepository.save(concertInfo);

    ConcertLocInfo concertLoc = ConcertLocInfo.from(form, concertInfo);

    concertLocRepository.save(concertLoc);

    ConcertSeatInfo concertSeat = ConcertSeatInfo.from(form, concertLoc);

    concertSeatRepository.save(concertSeat);

    List<LocalDateTime> concertDateInfo = new ArrayList<>();
    concertDateInfo.add(form.getStartDate());
    concertDateInfo.add(form.getEndDate());

    ConcertDateInfo concertDate = ConcertDateInfo.from(form,concertDateInfo, concertLoc);

    concertDateRepository.save(concertDate);

      return true;
  }


  @Transactional
  public boolean updateConcert(ConcertUpdateForm form, MultipartFile profileImage) {

    if(form.getEndDate().isBefore(form.getStartDate())){
      throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
    }

    // 존재하지 않는 콘서트에 대해 업데이트 시도하는 경우

      String concertImagePath = uploadProfileImage(profileImage);


    // 콘서트 정보 업데이트
   if(concertInfoRepository.findById(form.getId()).isEmpty()) {
     throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
   }

      ConcertInfo concertInfo = concertInfoRepository.findById(form.getId())
              .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

      Integer likeCount = concertInfoRepository.findById(form.getId()).get().getLikeCount();

      Integer reviewCount = concertInfoRepository.findById(form.getId()).get().getReviewCount();

      concertInfo.setTitle(form.getTitle());
      concertInfo.setArtist(form.getArtist());
      concertInfo.setStartDate(form.getStartDate());
      concertInfo.setEndDate(form.getEndDate());
      concertInfo.setLikeCount(likeCount);
      concertInfo.setReviewCount(reviewCount);
      concertInfo.setConcertImage(concertImagePath);
      concertInfo.setConcertType(form.getConcertType());


      concertInfoRepository.save(concertInfo);


    // 위치 업데이트

      ConcertLocInfo concertLocInfo = concertLocRepository.findById(form.getId())
              .orElseThrow(()->new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND));

      if(!Objects.equals(concertLocInfo.getId(), form.getId())) {
      throw new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND);
    }

      concertLocInfo.setLocation(form.getLocation());
      concertLocInfo.setConcertTitle(form.getTitle());

    concertLocInfoRepository.save(concertLocInfo);




    // 날짜 업데이트

      ConcertDateInfo concertDateInfo = concertDateRepository.findById(form.getId()).get();

    if(!Objects.equals(concertDateInfo.getId(), form.getId())){
      throw new CustomException(ErrorCode.CONCERT_DATE_NOT_FOUND);
    }
    List<LocalDateTime> concertDateInfoList = new ArrayList<>();
      concertDateInfoList.add(form.getStartDate());
      concertDateInfoList.add(form.getEndDate());

      HashMap<String, List<LocalDateTime>> concertTime = new HashMap<>();
      concertTime.put(form.getTitle(), concertDateInfoList);

      concertDateInfo.setConcertName(form.getTitle());
      concertDateInfo.setConcertTime(concertTime);
      concertDateInfo.setConcertStartDate(form.getStartDate());
      concertDateInfo.setConcertEndDate(form.getEndDate());

    concertDateRepository.save(concertDateInfo);


    // 좌석 업데이트

      ConcertSeatInfo concertSeatInfo = concertSeatRepository.findById(form.getId()).get();

    if(!Objects.equals(concertSeatInfo.getId(), form.getId())){
      throw new CustomException(ErrorCode.CONCERT_SEAT_NOT_FOUND);
    }

      concertSeatInfo.setSeatS(form.getSeatS());
      concertSeatInfo.setSeatSPrice(form.getSeatSPrice());
      concertSeatInfo.setSeatA(form.getSeatA());
      concertSeatInfo.setSeatAPrice(form.getSeatAPrice());
      concertSeatInfo.setSeatB(form.getSeatB());
      concertSeatInfo.setSeatBPrice(form.getSeatBPrice());


    concertSeatRepository.save(concertSeatInfo);

    return true;
  }




}
