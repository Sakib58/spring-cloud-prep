package com.springcloudprep.currencyconversion.payloads;

import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConvertDto {
    private CurrencyName fromCurrency;
    private CurrencyName toCurrency;
    private double amount;
    private double convertedAmount;
}
