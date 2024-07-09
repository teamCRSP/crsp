package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ReportAccepted;
import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportAcceptedRepository extends JpaRepository<ReportAccepted, Long> {

  ReportAccepted findByUser(User user); //

  List<ReportAccepted> findByCompareDate(LocalDateTime localDateTime);  // 지정한 기간이 지난 데이터 찾기
}
