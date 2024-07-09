package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Love;
import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoveRepository extends JpaRepository<Love, Long> {

  Love findByUserAndConcertInfo(User user, ConcertInfo concertInfo);

  int countByConcertInfo(ConcertInfo concertInfo);

}
