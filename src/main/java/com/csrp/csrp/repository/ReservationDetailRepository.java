package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ReservationDetail;
import com.csrp.csrp.entity.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Long> {

  Optional<List<ReservationDetail>> findByReservationHistory(ReservationHistory history);
}
