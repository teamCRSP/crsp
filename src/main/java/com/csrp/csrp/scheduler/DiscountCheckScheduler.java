package com.csrp.csrp.scheduler;

import com.csrp.csrp.entity.Discount;
import com.csrp.csrp.repository.ConcertInfoRepository;
import com.csrp.csrp.repository.DiscountRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountCheckScheduler {

  private final DiscountRepository discountRepository;
  private final ConcertInfoRepository concertInfoRepository;

  // 할인기간 끝을 확인하는 스케줄러
  //@Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
  @Scheduled(fixedDelay = 60000)
  public void checkAndActiveDiscount(){

    LocalDateTime today = LocalDateTime.now();

    List<Discount> discountActive = discountRepository.findByDiscountActiveTrue();




   /*   System.out.println("!@#!@#!@#@!#!@#@!#!@#!@#! 1");
    List<Discount> discountList = discountActive.stream()
            .filter(discount -> today.isEqual(discount.getDiscountEnd()))
            .peek(discount -> {
                System.out.println("!@#!@#!@#@!#!@#@!#!@#!@#! 2");

              discount.setDiscountActive(false);

              ConcertInfo concertInfo = concertInfoRepository.findById(discount.getConcertInfo().getId())
                      .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

              concertInfo.setSeatSPrice(concertInfo.getSeatSPrice() + discount.getDiscountAmount());
              concertInfo.setSeatAPrice(concertInfo.getSeatAPrice() + discount.getDiscountAmount());
              concertInfo.setSeatBPrice(concertInfo.getSeatBPrice() + discount.getDiscountAmount());

              concertInfoRepository.save(concertInfo);
              discountRepository.save(discount);
            })
            .toList();

      discountRepository.saveAll(discountList);
*/
    /*List<Discount> discountList = discountActive.stream().filter(discount ->{
        LocalDateTime discountEnd = discount.getDiscountEnd();
        return today.isEqual(discountEnd);
    }).toList();

    // 할인 기간이 끝나면 원래 가격으로 복귀
    discountList.forEach(discount -> {

      discount.setDiscountActive(false);

      ConcertInfo concertInfo = concertInfoRepository.findById(discount.getConcertInfo().getId())
          .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

      System.out.println("!@#!@#!@#@!@##@! ! "+concertInfo.getSeatSPrice());
      System.out.println("!@#!@#!@#@!@##@! ! "+concertInfo.getSeatAPrice());
      System.out.println("!@#!@#!@#@!@##@! ! "+concertInfo.getSeatBPrice());

      concertInfo.setSeatSPrice(concertInfo.getSeatSPrice() + discount.getDiscountAmount());
      concertInfo.setSeatAPrice(concertInfo.getSeatAPrice() + discount.getDiscountAmount());
      concertInfo.setSeatBPrice(concertInfo.getSeatBPrice() + discount.getDiscountAmount());

      concertInfoRepository.save(concertInfo);
      discountRepository.save(discount);
    });
*/
  }
}
