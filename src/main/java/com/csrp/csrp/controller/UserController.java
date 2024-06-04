package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.SignInRequestDTO;
import com.csrp.csrp.dto.request.SignUpRequestDTO;
import com.csrp.csrp.dto.response.SignInResponseDTO;
import com.csrp.csrp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

// 회원가입
  @PostMapping("/signUp")
  public ResponseEntity<?>signUp(
      @RequestPart("user") @Validated SignUpRequestDTO signUpRequestDTO,
      @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
  ) {
    String profilePath = null;
    if (profileImage != null) {
      profilePath = userService.uploadProfileImage(profileImage);
    }
    boolean result = userService.SignUp(signUpRequestDTO, profilePath);
    return ResponseEntity.ok().body(result);
  }

  // 로그인
  @PostMapping("/signIn")
  public ResponseEntity<?>signIn(
      @RequestBody @Validated SignInRequestDTO signInRequestDTO
      ) {
    SignInResponseDTO signInResponseDTO = userService.signIn(signInRequestDTO);
    return ResponseEntity.ok().body(signInResponseDTO);
  }
}
