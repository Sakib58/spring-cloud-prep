package com.springcloudprep.currencyconversion.controllers;

import com.springcloudprep.currencyconversion.models.CurrencyInfo;
import com.springcloudprep.currencyconversion.payloads.CurrencyConvertDto;
import com.springcloudprep.currencyconversion.payloads.CurrencyDto;

import com.springcloudprep.currencyconversion.payloads.CurrencyUpdateDto;
import com.springcloudprep.currencyconversion.services.CurrencyConversionService;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency-conversion")
@AllArgsConstructor
public class CurrencyConversionController {
    private final CurrencyConversionService currencyConversionService;

    @PostMapping("/add-currency")
    public ResponseEntity<?> addCurrency(@RequestBody CurrencyDto currencyDto) {
        return ResponseEntity.ok(currencyConversionService.saveCurrency(currencyDto));
    }

    @PostMapping("/update-currency")
    public ResponseEntity<?> updateCurrency(@RequestBody CurrencyUpdateDto currencyUpdateDto) {
        return ResponseEntity.ok(currencyConversionService.updateCurrency(currencyUpdateDto));
    }

    @GetMapping("/convert/{fromCurrency}/{toCurrency}/{amount}")
    public CurrencyConvertDto convertCurrency(
            @PathVariable CurrencyName fromCurrency,
            @PathVariable CurrencyName toCurrency,
            @PathVariable double amount
    ) {
        return currencyConversionService.convertCurrency(fromCurrency, toCurrency, amount);
    }
}
