package com.springcloudprep.currencyconversion.models;

import com.springcloudprep.sharedpackages.enums.CountryName;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CountryName countryName;
    @Column(unique = true)
    private CurrencyName currencyName;
    private double equivalentDollar;
}
