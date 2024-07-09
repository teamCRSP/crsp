package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ReservationHistory;
import com.csrp.csrp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {

  Page<ReservationHistory> findByUser(User user, Pageable pageRequest);

}
