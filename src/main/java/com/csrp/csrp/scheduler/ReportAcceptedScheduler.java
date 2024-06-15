package com.csrp.csrp.scheduler;

import com.csrp.csrp.entity.ReportAccepted;
import com.csrp.csrp.repository.ReportAcceptedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ReportAcceptedScheduler {
  private final ReportAcceptedRepository reportAcceptedRepository;
  //누적횟수가 3이상이고 일주일 지나면 자동 삭제
  @Async
  @Scheduled(cron = "0 0 0 * * *")   // 매일 자정에 실행
  public void AutomaticDeleteReportAccepted() {
    List<ReportAccepted> findAcceptedList = reportAcceptedRepository.findByCompareDate(LocalDateTime.now().minusDays(7));
    for (ReportAccepted reportAccepted : findAcceptedList) {
      reportAcceptedRepository.delete(reportAccepted);
    }
  }
}
