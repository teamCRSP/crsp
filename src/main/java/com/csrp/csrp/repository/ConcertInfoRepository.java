package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertInfo;
import java.time.LocalDateTime;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, Long> {

  @Query(value = "DELETE FROM ConcertInfo c WHERE c.createdDate <= :cutoffDate", nativeQuery = true)
  void deleteOldConcertInfos(@Param("cutoffDate") LocalDateTime cutoffDate);

  Optional<ConcertInfo> findByTitle(String concertTitle);
}
