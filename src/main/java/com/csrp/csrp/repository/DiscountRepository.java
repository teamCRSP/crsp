package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Discount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
  List<Discount> findByDiscountActiveTrue();
}
