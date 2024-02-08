package com.springcloudprep.currencyconversion.payloads;

public enum CurrencyName {
    TAKA(0),
    DOLLAR(1),
    EURO(2),
    CAD(3);
    private Integer value;

    CurrencyName(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
    public static CurrencyName getByValue(Integer value){
        for (CurrencyName currencyName: CurrencyName.values()){
            if (currencyName.value == value){
                return currencyName;
            }
        }
        throw new IllegalArgumentException("Currency name not found with that value");
    }
}
