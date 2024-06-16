package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertLocInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConcertLocInfoRepository extends JpaRepository<ConcertLocInfo, Long> {

  boolean existsByLocation(String concertLocation);

  List<ConcertLocInfo> findByConcertInfoId(Long concertInfoId);

  Optional<ConcertLocInfo> findByConcertTitleAndLocation(String concertName,
      String concertLocation);

  boolean existsByConcertTitle(String concertName);


  void deleteByLocationAndConcertTitle(String location, String concertTitle);

}