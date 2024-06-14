package com.csrp.csrp.service;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Discount;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.form.DiscountForm;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.DiscountRepository;
import com.csrp.csrp.type.ErrorCode;
import java.time.LocalDateTime;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

  private final ConcertInfoRepository concertInfoRepository;

  private final DiscountRepository discountRepository;

  @Transactional
  public Boolean concertDiscount(Long id, DiscountForm discountForm) {

    if (concertInfoRepository.findById(id).isEmpty()){
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
        .orElseThrow(()->new CustomException(ErrorCode.CONCERT_NOT_FOUND));


    LocalDateTime registerStart = concertInfo.getStartDate();
    LocalDateTime registerEnd = concertInfo.getEndDate();

    LocalDateTime discountStart = discountForm.getDiscountStart();
    LocalDateTime discountEnd = discountForm.getDiscountEnd();
    Integer discountAmount = discountForm.getDiscountAmount(); // 할인 금액

    LocalDateTime today = LocalDateTime.now();


    Integer sPrice = concertInfo.getSeatSPrice();
    Integer aPrice = concertInfo.getSeatAPrice();
    Integer bPrice = concertInfo.getSeatBPrice();




    if (discountStart.isBefore(today) && discountEnd.isBefore(today)){
      throw new CustomException(ErrorCode.DISCOUNT_DATE_NOT_VALID);
    }

    if (discountStart.isBefore(registerStart) && discountEnd.isBefore(registerStart)){
      throw new CustomException(ErrorCode.DISCOUNT_DATE_NOT_VALID);
    }

    if (discountStart.isAfter(registerEnd) && discountEnd.isAfter(registerEnd)){
      throw new CustomException(ErrorCode.DISCOUNT_DATE_NOT_VALID);
    }

    if (sPrice < discountAmount || aPrice < discountAmount || bPrice < discountAmount){
      throw new CustomException(ErrorCode.DISCOUNT_PRICE_NOT_VALID);
    }

    Discount discount = Discount.from(discountForm, concertInfo);

    discount.setDiscountActive(true);

    discountRepository.save(discount);


    concertInfo.setSeatSPrice(sPrice - discountAmount);
    concertInfo.setSeatAPrice(aPrice - discountAmount);
    concertInfo.setSeatBPrice(bPrice - discountAmount);

    concertInfoRepository.save(concertInfo);


    Integer sPriceNow = concertInfo.getSeatSPrice();
    Integer aPriceNow = concertInfo.getSeatAPrice();
    Integer bPriceNow = concertInfo.getSeatBPrice();



    // 할인 적용 안된 경우
    if (sPrice.equals(sPriceNow) || aPrice.equals(aPriceNow) || bPrice.equals(bPriceNow)){
      throw new CustomException(ErrorCode.DATABASE_UPDATE_FAIL);
    }


    return true;
  }
}
