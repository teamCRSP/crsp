package com.csrp.csrp.service;

import com.csrp.csrp.exception.DbException;
import java.util.UUID;

import com.csrp.csrp.form.ConcertUpdateForm;
import org.springframework.web.multipart.MultipartFile;
import com.csrp.csrp.entity.ConcertDateInfo;
import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.ConcertLocInfo;
import com.csrp.csrp.entity.ConcertSeatInfo;
import com.csrp.csrp.exception.ConcertException;
import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.repository.ConcertDateInfoRepository;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.ConcertLocInfoRepository;
import com.csrp.csrp.repository.ConcertSeatInfoRepository;
import com.csrp.csrp.type.ErrorCode;
import java.util.HashMap;
import java.util.List;
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

  // 파일명 변경 (사진 이름 중복 방지)
  public String uploadProfileImage(MultipartFile profileImage) {
    String profilePath = null;
    if (profileImage != null) {
      profilePath = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
    }
    return profilePath;
  }

  @Transactional
  public void registerConcert(ConcertForm form, MultipartFile profileImage) {

    // 등록할 콘서트 중복 체크
    // 같은 콘서트라면 제목, 장소, 개최 일시(예: 2024-06-08T12:30)가 같다
//    if (concertInfoRepository.existsByTitle(form.getTitle()) && concertLocRepository.existsByLocation(form.getLocation()) && concertDateRepository.existsByConcertDate(form.getDate())){
//      throw new ConcertException(ErrorCode.CONCERT_ALREADY_EXISTS);
//    }

    String concertImagePath = uploadProfileImage(profileImage);

    ConcertInfo concertInfo = ConcertInfo.from(form,concertImagePath);

    ConcertSeatInfo concertSeat = ConcertSeatInfo.from(form, concertInfo);

    ConcertDateInfo concertDate = ConcertDateInfo.from(form, concertInfo);

    ConcertLocInfo concertLoc = ConcertLocInfo.from(form, concertInfo);

    concertInfoRepository.save(concertInfo);

    concertDateRepository.save(concertDate);

    concertSeatRepository.save(concertSeat);

    concertLocRepository.save(concertLoc);

  }


  @Transactional
  public ConcertInfo updateConcert(ConcertUpdateForm form, MultipartFile profileImage) {

    // 존재하지 않는 콘서트에 대해 업데이트 시도하는 경우
    concertInfoRepository.findById(form.getId())
            .orElseThrow(() -> new ConcertException(ErrorCode.CONCERT_NOT_FOUND));

    String concertImagePath = uploadProfileImage(profileImage);
    ConcertInfo update = form.from(form, concertImagePath);
    ConcertInfo concertInfo = concertInfoRepository.save(update);


    // 콘서트 제목, 개최일시, 장소 3개의 정보가 일치할 경우만 update 가능

//    int result = modifyConcertInfo(form, concertImagePath);
//
//    // 업데이트 실패
//    if(result < 0){
//      throw new DbException(ErrorCode.DATABASE_UPDATE_FAIL);
//    }
//
//    ConcertInfo concertInfo = concertInfoRepository.findbyTitleAndLocationAndDate(
//        form.getTitle(), form.getLocation(), form.getDate())
//        .orElseThrow(()->new DbException(ErrorCode.DATABASE_SELECT_FAIL));

    return concertInfo;
  }

//  @Transactional
//  private int modifyConcertInfo(ConcertForm form, String concertImagePath) {
//
//
//
//    int result = concertInfoRepository.updateConcertInfo(
//        form.getArtist(),
//        form.getDescription(),
//        form.getAmount(),
//        concertImagePath,
//        0,
//        0,
//        form.getConcertType(),
//        form.getTitle(),
//        form.getLocation()
//    );
//
//    if(result > 0){
//      return 1;
//    }
//
//    return -1;
//  }


}
