package com.springcloudprep.paymentprocessing.service;

import com.springcloudprep.paymentprocessing.payloads.CurrencyConvertDto;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CURRENCY-CONVERSION", path = "/api/currency-conversion")
public interface CurrencyConversionClient {
    @GetMapping("/convert/{fromCurrency}/{toCurrency}/{amount}")
    public CurrencyConvertDto convertCurrency(
            @PathVariable CurrencyName fromCurrency,
            @PathVariable CurrencyName toCurrency,
            @PathVariable double amount
    );
}
