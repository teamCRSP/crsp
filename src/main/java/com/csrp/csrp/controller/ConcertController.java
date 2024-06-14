package com.csrp.csrp.controller;

import com.csrp.csrp.form.EachConcertInfoForm;
import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.form.ConcertUpdateForm;
import com.csrp.csrp.form.EachConcertUpdateForm;
import com.csrp.csrp.service.ConcertService;
import com.csrp.csrp.token.TokenProvider;
import com.csrp.csrp.token.TokenUserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {

  private final ConcertService concertService;

  private final TokenProvider tokenProvider;
  public static final String TOKEN_PREFIX = "Bearer ";

  @PostMapping("/register")
  public ResponseEntity<?> registerConcert(
      @RequestPart(value = "concert") @Valid ConcertForm form,
      @RequestHeader(name = "Authorization") String token,
      @RequestPart(value = "concertImage", required = false) MultipartFile concertImage
  ){

    token = token.substring(TOKEN_PREFIX.length());

    TokenUserInfo tokenAccountUserInfo = tokenProvider.getTokenAccountUserInfo(token);

    return ResponseEntity.ok(concertService.registerConcert(form,tokenAccountUserInfo, concertImage));
  }

  @PostMapping("/register/eachConcert")
  public ResponseEntity<?> registerEachConcert(
      @RequestParam("concertName") String concertName,
      @RequestParam("concertLocation") String concertLocation,
      @RequestPart(value = "register") @Valid EachConcertInfoForm form
  ) {
    concertService.registerEachConcert(concertName, concertLocation, form);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/update")
  public ResponseEntity<?> updateConcert(
      @RequestPart(value = "concert") @Valid ConcertUpdateForm form,
      @RequestPart(value = "concertImage", required = false) MultipartFile concertImage
  ){
    return ResponseEntity.ok(concertService.updateConcert(form, concertImage));
  }

  @PutMapping("/update/eachConcert")
  public ResponseEntity<?> updateEachConcert(
      @RequestParam("concertName") String concertName,
      @RequestParam("concertLocation") String concertLocation,
      @RequestPart(value = "update") @Valid EachConcertUpdateForm form
  ){
    return ResponseEntity.ok(concertService.updateEachConcert(concertName, concertLocation, form));
  }
}
