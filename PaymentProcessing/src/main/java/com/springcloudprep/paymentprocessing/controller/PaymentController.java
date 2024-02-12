package com.springcloudprep.paymentprocessing.controller;

import com.springcloudprep.paymentprocessing.payloads.TransactionDto;
import com.springcloudprep.paymentprocessing.service.PaymentService;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payment-process")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay/{amount}/{toCurrency}")
    public ResponseEntity<?> processPayment(
            @PathVariable double amount,
            @PathVariable CurrencyName toCurrency,
            @RequestBody TransactionDto transactionDto
            )
    {
        paymentService.payCosting(amount, toCurrency, transactionDto);
        return ResponseEntity.ok().body("Payment successful!");
    }
}
