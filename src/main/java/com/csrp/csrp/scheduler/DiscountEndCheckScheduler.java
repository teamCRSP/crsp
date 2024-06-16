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
public class DiscountEndCheckScheduler {

  private final ConcertInfoRepository concertInfoRepository;

  private final DiscountService discountService;

  private final DiscountRepository discountRepository;

  @Scheduled(cron = "0 0 0 * * *")
  public void checkAndActiveDiscount(){

    LocalDate today = LocalDate.now();

    List<Discount> discountActive = discountRepository.findByDiscountActiveTrue();


    List<Discount> discountList = discountActive.stream()
        .filter(discount -> {
          LocalDate discountEnd = discount.getDiscountEnd();
          long difference = ChronoUnit.DAYS.between(discountEnd, today);

          return difference == 0;
        }).toList();


      for (Discount discount : discountList){

        discount.setDiscountActive(false);

        ConcertInfo concertInfo = concertInfoRepository.findById(discount.getConcertInfo().getId())
            .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

        concertInfo.setSeatSPrice(concertInfo.getSeatSPrice() + discount.getDiscountAmount());
        concertInfo.setSeatAPrice(concertInfo.getSeatAPrice() + discount.getDiscountAmount());
        concertInfo.setSeatBPrice(concertInfo.getSeatBPrice() + discount.getDiscountAmount());

        discountService.saveSeparateSchedulerConcertInfo(concertInfo);
        discountService.saveSeparateSchedulerDiscount(discount);
      }

  }
}
