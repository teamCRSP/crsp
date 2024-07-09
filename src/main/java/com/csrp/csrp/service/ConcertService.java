package com.csrp.csrp.service;

import com.csrp.csrp.entity.EachConcertInfo;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.form.EachConcertInfoForm;
import com.csrp.csrp.form.EachConcertUpdateForm;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenUserInfo;

import java.time.LocalDateTime;
import java.util.*;

import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.form.ConcertUpdateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
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
    public boolean registerConcert(ConcertForm form, TokenUserInfo tokenAccountUserInfo,
                                   MultipartFile profileImage) {

        String concertImagePath = uploadProfileImage(profileImage);

        Long id = tokenAccountUserInfo.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));

        ConcertInfo concertInfo = ConcertInfo.from(form, concertImagePath, user);

        concertInfoRepository.save(concertInfo);

        for (String location : form.getLocation()) {
            ConcertLocInfo concertLoc = ConcertLocInfo.from(form, location, concertInfo);
            concertLocRepository.save(concertLoc);

        }

        return true;
    }


    @Transactional
    public boolean registerEachConcert(String concertName, String concertLocation,
                                       EachConcertInfoForm form) {

        if (form.getEndDate().isBefore(form.getStartDate())) {
            throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
        }

        if (!concertLocInfoRepository.existsByConcertTitle(concertName)
                && !concertLocInfoRepository.existsByLocation(concertLocation)) {
            throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
        }

        ConcertInfo concertInfo = concertInfoRepository.findByTitle(concertName)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

        LocalDateTime startDate = form.getStartDate();
        LocalDateTime endDate = form.getEndDate();

        // 전체 콘서트 일정안에 각각의 개최하는 장소가 다른 콘서트들은 전체 일정 안에 있어야 한다
        if (startDate.isBefore(concertInfo.getStartDate()) && endDate.isAfter(
                concertInfo.getEndDate())) {
            throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
        }

        ConcertLocInfo concertLocInfo = concertLocInfoRepository.findByConcertTitleAndLocation(concertName, concertLocation)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

        EachConcertInfo eachConcertInfo = EachConcertInfo.from(form, concertLocInfo.getConcertTitle(),
                concertLocInfo.getLocation(), concertLocInfo);

        eachConcertInfoRepository.save(eachConcertInfo);


        return true;
    }

    @Transactional
    public boolean updateConcert(ConcertUpdateForm form, MultipartFile profileImage) {

        String concertImagePath = uploadProfileImage(profileImage);


        // 콘서트 정보 업데이트

        ConcertInfo concertInfo = concertInfoRepository.findById(form.getConcertInfoId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

        Integer likeCount = concertInfoRepository.findById(form.getConcertInfoId()).get()
                .getLikeCount();

        Integer reviewCount = concertInfoRepository.findById(form.getConcertInfoId()).get()
                .getReviewCount();


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

        ConcertInfo save = concertInfoRepository.save(concertInfo);

        // 수정

        int location = 0;
        for (ConcertLocInfo locInfo : concertLocRepository.findByConcertInfoId(save.getId())) {
            // 콘서트 위치 수정
            ConcertLocInfo saveConcertLocInfo = concertLocRepository.save(ConcertLocInfo.builder()
                    .id(locInfo.getId())
                    .concertInfo(save)
                    .concertTitle(save.getTitle())
                    .location(form.getLocation().get(location))
                    .build());
            concertLocRepository.save(saveConcertLocInfo);

            // 개별 콘서트 concertLocation, concertName, concertLocInfo 수정
            EachConcertInfo eachConcertInfo = eachConcertInfoRepository.findByConcertLocInfo(saveConcertLocInfo);
            if (eachConcertInfo != null) {
                eachConcertInfoRepository.save(EachConcertInfo.builder()
                        .id(eachConcertInfo.getId())
                        .concertLocInfo(saveConcertLocInfo)
                        .concertStartDate(eachConcertInfo.getConcertStartDate())
                        .concertEndDate(eachConcertInfo.getConcertEndDate())
                        .concertLocation(saveConcertLocInfo.getLocation())
                        .concertName(saveConcertLocInfo.getConcertTitle())
                        .seatS(eachConcertInfo.getSeatS())
                        .seatA(eachConcertInfo.getSeatA())
                        .seatB(eachConcertInfo.getSeatB())
                        .build());
            }
            location++;
        }

        for (int i = location; i < form.getLocation().size(); i++) {
            concertLocRepository.save(ConcertLocInfo.builder()
                    .concertInfo(save)
                    .concertTitle(save.getTitle())
                    .location(form.getLocation().get(i))
                    .build());
        }

        return true;
    }

    @Transactional
    public boolean updateEachConcert(String concertName, String concertLocation,
                                     EachConcertUpdateForm form) {
        if (form.getEndDate().isBefore(form.getStartDate())) {
            throw new CustomException(ErrorCode.CONCERT_DATE_NOT_VALID);
        }

        EachConcertInfo eachConcertInfo = eachConcertInfoRepository.findByConcertNameAndConcertLocation(
                        concertName, concertLocation)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

        String findLocation = eachConcertInfo.getConcertLocation();

        ConcertLocInfo concertLocInfo = concertLocInfoRepository.findByConcertTitleAndLocation(concertName, findLocation)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_LOCATION_NOT_FOUND));

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
