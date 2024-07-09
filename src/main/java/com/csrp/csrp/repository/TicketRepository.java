package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Ticket;
import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findByUser(User user);

  List<Ticket> findByEndDateBefore(LocalDateTime now);  // 지금 시간이 endDate 와 같은 ticket 찾기
}
