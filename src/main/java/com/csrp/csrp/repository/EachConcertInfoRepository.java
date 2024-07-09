package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertLocInfo;
import com.csrp.csrp.entity.EachConcertInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EachConcertInfoRepository extends JpaRepository<EachConcertInfo, Long> {

  Optional<EachConcertInfo> findByConcertNameAndConcertLocation(String concertName, String concertLocation);

  EachConcertInfo findByConcertLocInfo(ConcertLocInfo saveConcertLocInfo);

}
