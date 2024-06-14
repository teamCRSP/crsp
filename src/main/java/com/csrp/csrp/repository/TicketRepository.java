package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Ticket;
import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> findByUser(User user);
}
