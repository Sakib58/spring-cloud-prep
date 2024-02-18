package com.springcloudprep.notificationservice.model;

import com.springcloudprep.sharedpackages.enums.CurrencyName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private CurrencyName fromCurrency;
    private CurrencyName toCurrency;
    private double amount;
    private double amountInOwnCurrency;
}
