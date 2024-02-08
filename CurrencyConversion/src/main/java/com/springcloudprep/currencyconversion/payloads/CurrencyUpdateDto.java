package com.springcloudprep.currencyconversion.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyUpdateDto {
    private Integer currencyId;
    private Long equivalentDollar;
}
