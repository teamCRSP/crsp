package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<ConcertOrganizer, Long> {

}
