package com.csrp.csrp.service;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Discount;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.form.DiscountForm;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.DiscountRepository;
import com.csrp.csrp.type.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

  private final ConcertInfoRepository concertInfoRepository;

  private final DiscountRepository discountRepository;


  @Transactional
  public void saveSeparateSchedulerConcertInfo(ConcertInfo concertInfo) {
    concertInfoRepository.save(concertInfo);
  }

  @Transactional
  public void saveSeparateSchedulerDiscount(Discount discount) {
    discountRepository.save(discount);
  }

  @Transactional
  public boolean concertDiscount(Long id, DiscountForm discountForm) {

    if (concertInfoRepository.findById(id).isEmpty()) {
      throw new CustomException(ErrorCode.CONCERT_NOT_FOUND);
    }

    /*
     * discountStart, discountEnd 이 LocalDateTime.now() 보다는 After 이어야 한다
     * discountStart, discountEnd 이 registerStart 보다는 After 이어야 한다
     * discountStart, discountEnd 이 endDate 보다는 before 이어야 한다
     * discountPrice <= amount
     *
     * 할인 수량 <= 티켓 수량
     * */

    ConcertInfo concertInfo = concertInfoRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

    LocalDateTime registerStart = concertInfo.getStartDate();
    LocalDateTime registerEnd = concertInfo.getEndDate();

    LocalDate discountStart = discountForm.getDiscountStart();
    LocalDate discountEnd = discountForm.getDiscountEnd();
    Integer discountAmount = discountForm.getDiscountAmount(); // 할인 금액

    LocalDate today = LocalDate.now();

    Integer sPrice = concertInfo.getSeatSPrice();
    Integer aPrice = concertInfo.getSeatAPrice();
    Integer bPrice = concertInfo.getSeatBPrice();

    // 할인 시작일과 할인 종료일이 현재일보다 이전일 수는 없다
    if (discountStart.isBefore(today) && discountEnd.isBefore(today)) {
      throw new CustomException(ErrorCode.DISCOUNT_DATE_NOT_VALID);
    }

    // 할인 시작일과 할인 종료일이 콘서트 개최일보다 이전일 수는 없다
    if (discountStart.isBefore(ChronoLocalDate.from(registerStart)) && discountEnd.isBefore(ChronoLocalDate.from(registerStart))) {
      throw new CustomException(ErrorCode.DISCOUNT_DATE_NOT_VALID);
    }

    //할인 시작일과 할인 종료일이 콘서트 종료일보다 이전일 수는 없다
    if (discountStart.isAfter(ChronoLocalDate.from(registerEnd)) && discountEnd.isAfter(ChronoLocalDate.from(registerEnd))) {
      throw new CustomException(ErrorCode.DISCOUNT_DATE_NOT_VALID);
    }

    if (sPrice < discountAmount || aPrice < discountAmount || bPrice < discountAmount) {
      throw new CustomException(ErrorCode.DISCOUNT_PRICE_NOT_VALID);
    }

    Discount discount = Discount.from(discountForm, concertInfo);

    discountRepository.save(discount);

    return true;
  }
}
