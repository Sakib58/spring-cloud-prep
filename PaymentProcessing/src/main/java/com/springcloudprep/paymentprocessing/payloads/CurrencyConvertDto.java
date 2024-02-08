package com.springcloudprep.paymentprocessing.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConvertDto {
    private String fromCountry;
    private String toCountry;
    private double amount;
    private double convertedAmount;
}
