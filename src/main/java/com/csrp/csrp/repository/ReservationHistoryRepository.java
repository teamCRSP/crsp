package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {

  List<ReservationHistory> findByUser(User user);

}
