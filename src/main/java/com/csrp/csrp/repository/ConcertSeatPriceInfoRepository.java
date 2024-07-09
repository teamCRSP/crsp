package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertSeatPriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertSeatPriceInfoRepository extends JpaRepository<ConcertSeatPriceInfo, Long> {

}
