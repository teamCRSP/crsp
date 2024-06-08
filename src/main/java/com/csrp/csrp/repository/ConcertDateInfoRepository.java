package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertDateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertDateInfoRepository extends JpaRepository<ConcertDateInfo, Long> {

}
