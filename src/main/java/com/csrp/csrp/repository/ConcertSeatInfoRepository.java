package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertSeatInfoRepository extends JpaRepository<ConcertSeatInfo, Long> {

}
