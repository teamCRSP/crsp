package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.type.ConcertType;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, Long> {

  boolean existsByTitle(String title);

  @Query("SELECT c.title FROM ConcertInfo c WHERE c.title = :title")
  Optional<String> findByTitle(@Param("title") String title);


  @Query("SELECT c FROM ConcertInfo c WHERE c.title = :title AND c.location = :location AND c.date = :date")
  Optional<ConcertInfo> findbyTitleAndLocationAndDate(String title, String location, LocalDateTime date);


  @Query(value = "DELETE FROM ConcertInfo c WHERE c.createdDate <= :cutoffDate", nativeQuery = true)
  void deleteOldConcertInfos(@Param("cutoffDate") LocalDateTime cutoffDate);
}
