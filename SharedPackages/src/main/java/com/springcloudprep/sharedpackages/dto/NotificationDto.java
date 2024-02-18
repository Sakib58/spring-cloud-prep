package com.springcloudprep.sharedpackages.dto;

import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long userId;
    private CurrencyName fromCurrency;
    private CurrencyName toCurrency;
    private double amount;
    private double amountInOwnCurrency;
}
