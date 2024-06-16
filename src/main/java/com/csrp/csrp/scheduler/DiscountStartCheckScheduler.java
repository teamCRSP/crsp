package com.csrp.csrp.scheduler;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Discount;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.DiscountRepository;
import com.csrp.csrp.service.DiscountService;
import com.csrp.csrp.type.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountStartCheckScheduler {

  private final ConcertInfoRepository concertInfoRepository;

  private final DiscountService discountService;

  private final DiscountRepository discountRepository;


  @Scheduled(cron = "0 0 0 * * *")
  public void checkAndActiveDiscount() {
    LocalDateTime today = LocalDateTime.now();

    List<Discount> discountActive = discountRepository.findByDiscountActiveFalse();

    List<Discount> discountList = discountActive.stream()
        .filter(discount -> {
          LocalDate discountStart = discount.getDiscountStart();
          long difference = ChronoUnit.DAYS.between(discountStart, today);

          return difference == 0;
        }).toList();


    for (Discount discount : discountList){

      discount.setDiscountActive(true);

      ConcertInfo concertInfo = concertInfoRepository.findById(discount.getConcertInfo().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

      Integer sPrice = concertInfo.getSeatSPrice();
      Integer aPrice = concertInfo.getSeatAPrice();
      Integer bPrice = concertInfo.getSeatBPrice();

      Integer discountAmount = discount.getDiscountAmount();

      concertInfo.setSeatSPrice(sPrice - discountAmount);
      concertInfo.setSeatAPrice(aPrice - discountAmount);
      concertInfo.setSeatBPrice(bPrice - discountAmount);

      discountService.saveSeparateSchedulerConcertInfo(concertInfo);
      discountService.saveSeparateSchedulerDiscount(discount);

      Integer sPriceNow = concertInfo.getSeatSPrice();
      Integer aPriceNow = concertInfo.getSeatAPrice();
      Integer bPriceNow = concertInfo.getSeatBPrice();

      // 할인 적용 안된 경우
      if (sPrice.equals(sPriceNow) || aPrice.equals(aPriceNow) || bPrice.equals(bPriceNow)) {
        throw new CustomException(ErrorCode.DATABASE_UPDATE_FAIL);
      }

    }
  }
}
