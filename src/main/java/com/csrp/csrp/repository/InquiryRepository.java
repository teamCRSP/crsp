package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Inquiry;
import com.csrp.csrp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
  Page<Inquiry> findByUser(User user, Pageable pageRequest);



}
