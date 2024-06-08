package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertDateInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertDateInfoRepository extends JpaRepository<ConcertDateInfo, Long> {

  boolean existsByConcertDate(LocalDateTime date);

  @Query(value = "SELECT c.date FROM ConcertDateInfo c WHERE c.date = :date", nativeQuery = true)
  Optional<LocalDateTime> findByDate(@Param("date") LocalDateTime date);
}
