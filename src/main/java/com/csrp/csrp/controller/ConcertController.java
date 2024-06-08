package com.csrp.csrp.controller;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.form.ConcertUpdateForm;
import com.csrp.csrp.service.ConcertService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {

  private final ConcertService concertService;

  @PostMapping("/register")
  public ResponseEntity<?> registerConcert(
      @RequestPart(value = "concert") @Valid ConcertForm form,
      @RequestPart(value = "concertImage", required = false) MultipartFile concertImage
  ){

      concertService.registerConcert(form, concertImage);

    return ResponseEntity.ok().build();
  }


  @PutMapping("/update")
  public ResponseEntity<ConcertInfo> updateConcert(
      @RequestPart(value = "concert") @Valid ConcertUpdateForm form,
      @RequestPart(value = "concertImage", required = false) MultipartFile concertImage
  ){
    return ResponseEntity.ok(concertService.updateConcert(form, concertImage));
  }

}
