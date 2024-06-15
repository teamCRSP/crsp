package com.csrp.csrp.repository;

import com.csrp.csrp.entity.PaymentHistory;
import com.csrp.csrp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentHistory, Long> {

  Page<PaymentHistory> findByUser(User user, Pageable pageRequest);
}
