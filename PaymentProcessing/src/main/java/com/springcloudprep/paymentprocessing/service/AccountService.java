package com.springcloudprep.paymentprocessing.service;

import com.springcloudprep.paymentprocessing.models.Account;
import com.springcloudprep.paymentprocessing.models.User;
import com.springcloudprep.paymentprocessing.payloads.TransactionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createNewAccount() {
        return accountRepository.save(Account.builder().build());
    }
}
