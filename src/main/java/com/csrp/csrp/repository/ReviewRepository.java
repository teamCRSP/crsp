package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT COUNT(r) FROM Review r WHERE r.user = :user AND r.sanction = true")
  int countByUserAndSanctionIsTrue(@Param("user") User user);

  List<Review> findByUserAndSanction(User user, boolean sanction);

  Page<Review> findByUser(User user, Pageable  pageRequest);

  Page<Review> findAll(Pageable pageRequest);
}
