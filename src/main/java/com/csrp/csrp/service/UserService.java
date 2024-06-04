package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.SignInRequestDTO;
import com.csrp.csrp.dto.request.SignUpRequestDTO;
import com.csrp.csrp.dto.response.SignInResponseDTO;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenProvider;
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
  private final TokenProvider tokenProvider;

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


  // 파일명 변경 (사진 이름 중복 방지)
  public String uploadProfileImage(MultipartFile profileImage) {
    String uniqueFileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();

    return uniqueFileName;
  }

  // 로그인 검증
  public SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
    String email = signInRequestDTO.getEmail();
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER_EMAIL));

    // 패스워드 검증
    String inputPassword = signInRequestDTO.getPassword();
    String encoderPassword = user.getPassword();

    if (!encoder.matches(inputPassword, encoderPassword)) {
      throw new CustomException(ErrorCode.NOT_ACCORD_USER_PASSWORD);
    }
    String token = tokenProvider.createToken(user);
    return new SignInResponseDTO(token, user);
  }
}
