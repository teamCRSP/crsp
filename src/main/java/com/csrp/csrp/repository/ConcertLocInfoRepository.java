package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertLocInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConcertLocInfoRepository extends JpaRepository<ConcertLocInfo, Long> {

  boolean existsByLocation(String concertLocation);

  List<ConcertLocInfo> findAllByConcertTitle(String concertName);

  ConcertLocInfo findByLocation(String findLocation);

}
