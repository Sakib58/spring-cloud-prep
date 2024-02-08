package com.springcloudprep.currencyconversion.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private Integer currencyId;
    private Integer countryId;
    private double equivalentDollar;
}
