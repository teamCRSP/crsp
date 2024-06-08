package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertLocInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertLocInfoRepository extends JpaRepository<ConcertLocInfo, Long> {

  boolean existsByLocation(String location);

  @Query("SELECT c.location FROM ConcertLocInfo c WHERE c.location = :location")
  Optional<String> findByLocation(@Param("location") String location);
}
