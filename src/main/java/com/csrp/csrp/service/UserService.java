package com.csrp.csrp.service;

import com.csrp.csrp.dto.request.SignInRequestDTO;
import com.csrp.csrp.dto.request.SignUpRequestDTO;
import com.csrp.csrp.dto.request.UserDeleteRequestDTO;
import com.csrp.csrp.dto.request.UserInfoModifyRequestDTO;
import com.csrp.csrp.dto.response.SignInResponseDTO;
import com.csrp.csrp.dto.response.UserInfoModifyResponseDTO;
import com.csrp.csrp.dto.response.UserShowResponseDTO;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.exception.CustomException;
import com.csrp.csrp.repository.UserRepository;
import com.csrp.csrp.token.TokenProvider;
import com.csrp.csrp.token.TokenUserInfo;
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
  public boolean SignUp(SignUpRequestDTO signUpRequestDTO, MultipartFile profileImage) {
    String email = signUpRequestDTO.getEmail();
    boolean exists = userRepository.existsByEmail(email);
    if (exists) {
      throw new CustomException(ErrorCode.EXISTS_USER_EMAIL);
    }
    // 패스워드 인코딩
    String encodedPassword = encoder.encode(signUpRequestDTO.getPassword());
    // 파일명 변경
    String profileImagePath = uploadProfileImage(profileImage);
    // dto를 entity로 변환
    User user = signUpRequestDTO.toEntity(profileImagePath, encodedPassword);
    userRepository.save(user);
    return true;
  }


  // 파일명 변경 (사진 이름 중복 방지)
  public String uploadProfileImage(MultipartFile profileImage) {
    String profilePath = null;
    if (profileImage != null) {
      profilePath = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
    }
    return profilePath;
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

  // 회원정보 수정
  public UserInfoModifyResponseDTO userInfoModify(UserInfoModifyRequestDTO userInfoModifyRequestDTO, MultipartFile profileImage, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    // 파일명 변경
    String profileImagePath = uploadProfileImage(profileImage);
    User modifyUser = userInfoModifyRequestDTO.toModifyEntity(user, userInfoModifyRequestDTO, profileImagePath);
    User save = userRepository.save(modifyUser);

    return new UserInfoModifyResponseDTO(save);
  }

  // 회원정보 삭제
  public boolean userDelete(UserDeleteRequestDTO userDeleteRequestDTO, TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    if (!user.getEmail().equals(userDeleteRequestDTO.getEmail())) {
      throw new CustomException(ErrorCode.NOT_ACCORD_USER_EMAIL);
    }
    String inputPassword = userDeleteRequestDTO.getPassword();
    String encoderPassword = user.getPassword();
    if (!encoder.matches(inputPassword, encoderPassword)) {
      throw new CustomException(ErrorCode.NOT_ACCORD_USER_PASSWORD);
    }
    userRepository.deleteById(user.getId());
    return true;
  }

  // 회원정보 보여주기
  public UserShowResponseDTO userShow(TokenUserInfo tokenUserInfo) {
    User user = userRepository.findById(tokenUserInfo.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER));
    return new UserShowResponseDTO(user);
  }

  // 이메일 중복체크
  public boolean emailCheck(String email) {
    return userRepository.existsByEmail(email);
  }
}
