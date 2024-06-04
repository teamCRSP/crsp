package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.SignUpRequestDTO;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  // 회원가입
  public boolean SignUp(SignUpRequestDTO signUpRequestDTO, String profileImage) {
    String email = signUpRequestDTO.getEmail();
    boolean exists = userRepository.existsByEmail(email);
    if (exists) {
      throw new CustomException(ErrorCode.EXISTS_USER_EMAIL);
    }
    // 패스워드 인코딩
    String encodedPassword = encoder.encode(signUpRequestDTO.getPassword());

    // dto를 entity로 변환
    User user = signUpRequestDTO.toEntity(profileImage, encodedPassword);
    userRepository.save(user);
    return true;
  }


  public String uploadProfileImage(MultipartFile profileImage) {
    // 파일명 변경 (사진 이름 중복 방지)
    String uniqueFileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();

    return uniqueFileName;
  }
}
