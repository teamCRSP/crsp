package com.csrp.csrp.repository;

import com.csrp.csrp.entity.Answer;
import com.csrp.csrp.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  Optional<Answer> findByInquiry(Inquiry inquiry);
}
