package com.csrp.csrp.scheduler;

import com.csrp.csrp.entity.Ticket;
import com.csrp.csrp.repository.TicketRepository;
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
public class TicketScheduler {
  private final TicketRepository ticketRepository;

  @Async
  @Scheduled(fixedDelay = 60000)  // 1분마다 실행
  public void automaticDeleteTicket() {

    LocalDateTime now = LocalDateTime.now();
    List<Ticket> expiredTickets = ticketRepository.findByEndDateBefore(now);

      ticketRepository.deleteAll(expiredTickets);

  }
}
