package com.csrp.csrp.repository;

import com.csrp.csrp.entity.ReportAccepted;
import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportAcceptedRepository extends JpaRepository<ReportAccepted, Long> {

  Integer countByUser(User user);

  ReportAccepted findByUser(User user);

  List<ReportAccepted> findByWarningCountAndModifiedAt(int warningCount, LocalDateTime localDateTime);  // 신고가 3,지정한 기간이 지난 데이터 조회
}
