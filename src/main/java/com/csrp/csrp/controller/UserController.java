package com.csrp.csrp.controller;

import com.csrp.csrp.dto.request.SignInRequestDTO;
import com.csrp.csrp.dto.request.SignUpRequestDTO;
import com.csrp.csrp.dto.request.UserDeleteRequestDTO;
import com.csrp.csrp.dto.request.UserInfoModifyRequestDTO;
import com.csrp.csrp.dto.response.SignInResponseDTO;
import com.csrp.csrp.dto.response.UserInfoModifyResponseDTO;
import com.csrp.csrp.dto.response.UserShowResponseDTO;
import com.csrp.csrp.service.UserService;
import com.csrp.csrp.token.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    boolean result = userService.SignUp(signUpRequestDTO, profileImage);
    return ResponseEntity.ok().body(result);
  }

  // 이메일 중복체크
  @GetMapping
  public ResponseEntity<?>emailCheck(
      @RequestParam String email
  ) {
    boolean result = userService.emailCheck(email);
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

  // 회원정보 수정
  @RequestMapping(value = "/userInfoModify", method = {RequestMethod.PATCH, RequestMethod.PUT})
  public ResponseEntity<?>userInfoModify(
      @RequestPart("user") @Validated UserInfoModifyRequestDTO userInfoModify,
      @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {

    UserInfoModifyResponseDTO userInfoModifyResponseDTO = userService.userInfoModify(userInfoModify, profileImage, tokenUserInfo);
    return ResponseEntity.ok().body(userInfoModifyResponseDTO);
  }

  // 회원정보 삭제
  @DeleteMapping("/delete")
  public ResponseEntity<?>userDelete(
      @RequestBody @Validated UserDeleteRequestDTO userDeleteRequestDTO,
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
      ) {
    boolean result = userService.userDelete(userDeleteRequestDTO, tokenUserInfo);
    return ResponseEntity.ok().body(result);
  }

  // 회원정보 뷰
  @GetMapping("/show")
  public ResponseEntity<?> userShow(
      @AuthenticationPrincipal TokenUserInfo tokenUserInfo
  ) {
    UserShowResponseDTO userShowResponseDTO = userService.userShow(tokenUserInfo);
    return ResponseEntity.ok().body(userShowResponseDTO);
  }
}
