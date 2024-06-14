package com.csrp.csrp.controller;

import com.csrp.csrp.form.DiscountForm;
import com.csrp.csrp.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {

  private final DiscountService discountService;

  @PostMapping("/{concertId}")
  public ResponseEntity<?> applyConcertDiscount(
      @PathVariable(name = "concertId") Long concertId,
      @RequestPart(value = "discount") @Valid DiscountForm discountForm
  ){
    return ResponseEntity.ok(discountService.concertDiscount(concertId, discountForm));
  }
}
