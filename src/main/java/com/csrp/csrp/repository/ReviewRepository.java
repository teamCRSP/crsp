package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Review;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.ReviewStopStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  int countByUserAndReviewStopStatusAndSanction(User user, ReviewStopStatus no, boolean b);

  List<Review> findByUserAndSanction(User user, boolean sanction);

  Page<Review> findByUser(User user, Pageable  pageRequest);

  Page<Review> findAll(Pageable pageRequest);
}
