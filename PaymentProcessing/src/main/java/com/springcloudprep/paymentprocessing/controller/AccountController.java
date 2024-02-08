package com.springcloudprep.paymentprocessing.controller;

import com.springcloudprep.paymentprocessing.models.Account;
import com.springcloudprep.paymentprocessing.payloads.TransactionDto;
import com.springcloudprep.paymentprocessing.service.AccountService;
import com.springcloudprep.paymentprocessing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    public final AccountService accountService;
    public final UserService userService;

    @PostMapping("/deposit")
    public String depositBalance(@RequestBody TransactionDto transactionDto) {
        userService.depositBalance(transactionDto);
        return "Deposit success!";
    }

    //todo: Implement controller for withdraw money
}
