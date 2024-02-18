package com.springcloudprep.paymentprocessing.service;

import com.springcloudprep.paymentprocessing.models.Account;
import com.springcloudprep.paymentprocessing.models.User;
import com.springcloudprep.paymentprocessing.payloads.CurrencyConvertDto;
import com.springcloudprep.paymentprocessing.payloads.TransactionDto;
import com.springcloudprep.sharedpackages.dto.NotificationDto;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {
    private final RestTemplate restTemplate;
    private final CurrencyConversionClient currencyConversionClient;
    private final UserService userService;
    private final AccountService accountService;
    public double calculateDeductionAmount(CurrencyName fromCurrency, CurrencyName toCurrency, double amount) {
        log.info("Currency conversion service is calling");
        // The following line is the way to communicate using restTemplate
        // CurrencyConvertDto currencyConvertDto = restTemplate.getForEntity("http://CURRENCY-CONVERSION/api/currency-conversion/convert/{fromCurrency}/{toCurrency}/{amount}", CurrencyConvertDto.class, fromCurrency, toCurrency, amount).getBody();

        // Reverse currency because user will pay by his own currency
        CurrencyConvertDto currencyConvertDto =  currencyConversionClient.convertCurrency(toCurrency, fromCurrency, amount);
        log.info("Currency conversion is successful!");
        return currencyConvertDto.getConvertedAmount();
    }

    public NotificationDto payCosting(double amount, CurrencyName toCurrency, TransactionDto transactionDto) {
        Optional<User> optionalUser = userService.findUserById(transactionDto.getUserId());
        if (optionalUser.isEmpty())
            throw new IllegalArgumentException("Transaction failed! User not found!");
        User user = optionalUser.get();
        double deductionAmount = calculateDeductionAmount(user.getCurrencyName(), toCurrency, amount);
        if (user.getAccount().getBalance() < deductionAmount)
            throw new RuntimeException("Transaction failed due to low balance!");
        Account accountAfterWithdraw = userService.withdrawBalance(TransactionDto.builder().userId(user.getId()).amount(deductionAmount).build());
        if (accountAfterWithdraw.getBalance() != user.getAccount().getBalance())
            throw new RuntimeException("Payment successful with error!");
        log.info("Payment successful!");
        return NotificationDto.builder()
                .userId(transactionDto.getUserId())
                .fromCurrency(user.getCurrencyName())
                .toCurrency(toCurrency)
                .amount(amount)
                .amountInOwnCurrency(deductionAmount)
                .build();
    }
}
