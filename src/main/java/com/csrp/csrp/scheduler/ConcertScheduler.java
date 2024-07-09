package com.csrp.csrp.scheduler;

import com.csrp.csrp.repository.ConcertInfoRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertScheduler {

  private final ConcertInfoRepository concertInfoRepository;

  // 6개월에 한 번씩 실행 (cron 표현식: 초 분 시 일 월 요일 연도)
  @Scheduled(cron = "0 0 0 1 */6 ?")
  public void deleteOldConcertInfos() {
    LocalDateTime cutoffDate = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);
    concertInfoRepository.deleteOldConcertInfos(cutoffDate);
    log.info("Old ConcertInfo records deleted.");
  }
}
