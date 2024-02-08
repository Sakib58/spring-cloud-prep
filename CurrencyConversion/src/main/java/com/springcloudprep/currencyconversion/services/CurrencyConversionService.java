package com.springcloudprep.currencyconversion.services;

import com.springcloudprep.currencyconversion.models.CurrencyInfo;
import com.springcloudprep.currencyconversion.payloads.*;
import com.springcloudprep.currencyconversion.repositories.CurrencyConversionRepo;
import com.springcloudprep.sharedpackages.enums.CountryName;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyConversionService {
    private final CurrencyConversionRepo repo;
    public CurrencyInfo saveCurrency(CurrencyDto currencyDto) {
        return repo.save(
                CurrencyInfo.builder()
                        .currencyName(CurrencyName.getByValue(currencyDto.getCurrencyId()))
                        .countryName(CountryName.getByValue(currencyDto.getCountryId()))
                        .equivalentDollar(currencyDto.getEquivalentDollar())
                        .build()
        );
    }

    public CurrencyInfo updateCurrency(CurrencyUpdateDto currencyUpdateDto) {
        CurrencyInfo currencyInfo = repo.findCurrencyInfoByCurrencyName(CurrencyName.getByValue(currencyUpdateDto.getCurrencyId()));
        if (currencyInfo == null)
            return null;
        currencyInfo.setEquivalentDollar(currencyInfo.getEquivalentDollar());
        return repo.save(currencyInfo);
    }

    public CurrencyConvertDto convertCurrency(CurrencyName fromCurrency, CurrencyName toCurrency, double amount) {
        CurrencyInfo fromCurrencyInfo = repo.findCurrencyInfoByCurrencyName(fromCurrency);
        CurrencyInfo toCurrencyInfo = repo.findCurrencyInfoByCurrencyName(toCurrency);
        if (fromCurrencyInfo == null || toCurrencyInfo == null)
            throw new RuntimeException("Can't able to convert currency!");
        double fromDollarAmount = fromCurrencyInfo.getEquivalentDollar() * amount;
        double convertedAmount = fromDollarAmount / toCurrencyInfo.getEquivalentDollar();
        return CurrencyConvertDto.builder().fromCurrency(fromCurrency).toCurrency(toCurrency).amount(amount).convertedAmount(convertedAmount).build();
    }
}
