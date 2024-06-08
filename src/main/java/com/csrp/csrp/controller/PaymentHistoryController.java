package com.csrp.csrp.controller;

import com.csrp.csrp.model.PaymentHistory;
import com.csrp.csrp.repository.PaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/payment-history")
public class PaymentHistoryController {

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @PostMapping
    public PaymentHistory creatPaymentHistory (@RequestBody PaymentHistory paymentHistory) {
        return paymentHistoryRepository.save(paymentHistory);
    }

    @GetMapping("/{id}")
    public PaymentHistory getPaymentHistory(@PathVariable Long id) {
        return paymentHistoryRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<PaymentHistory> getAllpaymentHistories() {
        return paymentHistoryRepository.findAll();
    }
}
