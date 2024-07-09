package com.csrp.csrp.repository;

import com.csrp.csrp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // 이메일이 존재하는지
  boolean existsByEmail(String email);
  // 이메일로 회원정보 조회
  Optional<User> findByEmail(String email);
}
