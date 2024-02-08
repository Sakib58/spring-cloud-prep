package com.springcloudprep.currencyconversion.repositories;

import com.springcloudprep.currencyconversion.models.CurrencyInfo;
import com.springcloudprep.sharedpackages.enums.CurrencyName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyConversionRepo extends JpaRepository<CurrencyInfo, Long> {
    public CurrencyInfo findCurrencyInfoByCurrencyName(CurrencyName currencyName);
}
