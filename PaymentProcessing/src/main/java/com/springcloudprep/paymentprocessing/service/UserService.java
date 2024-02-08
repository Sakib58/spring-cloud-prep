package com.springcloudprep.paymentprocessing.service;

import com.springcloudprep.paymentprocessing.models.Account;
import com.springcloudprep.paymentprocessing.models.User;
import com.springcloudprep.paymentprocessing.payloads.TransactionDto;
import com.springcloudprep.paymentprocessing.payloads.UserDto;
import com.springcloudprep.sharedpackages.enums.CountryName;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public User addNewUser(UserDto userDto) {
        Account account = accountService.createNewAccount();
        return userRepository.save(
                User.builder()
                        .name(userDto.getName())
                        .countryName(CountryName.getByValue(userDto.getCountryId()))
                        .currencyName(CurrencyName.getByValue(userDto.getCurrencyId()))
                        .account(account)
                        .build()
        );
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Account depositBalance(TransactionDto transactionDto) {
        Optional<User> optionalUser = findUserById(transactionDto.getUserId());
        if (optionalUser.isEmpty())
            throw new IllegalArgumentException("User not found!");
        Optional<Account> optionalAccount = accountRepository.findById(optionalUser.get().getAccount().getId());
        if (optionalAccount.isEmpty())
            throw new IllegalArgumentException("Account not found with this user id!");
        Account account = optionalAccount.get();
        account.setBalance(account.getBalance() + transactionDto.getAmount());
        return accountRepository.save(account);
    }

    public Account withdrawBalance(TransactionDto transactionDto) {
        Optional<User> optionalUser = findUserById(transactionDto.getUserId());
        if (optionalUser.isEmpty())
            throw new IllegalArgumentException("User not found!");
        Optional<Account> account = accountRepository.findById(optionalUser.get().getAccount().getId());
        if (account.isEmpty())
            throw new IllegalArgumentException("Account not found with this user id!");
        account.get().setBalance(account.get().getBalance() - transactionDto.getAmount());
        return accountRepository.save(account.get());
    }
}
