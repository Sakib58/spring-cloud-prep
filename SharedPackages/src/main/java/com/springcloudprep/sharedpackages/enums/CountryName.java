package com.springcloudprep.sharedpackages.enums;

public enum CountryName {
    BANGLADESH(0),
    USA(1),
    ENGLAND(2),
    CANADA(3);

    private Integer value;

    CountryName(Integer value) {
        this.value = value;
    }

    public static CountryName getByValue(Integer value) {
        for (CountryName countryName: CountryName.values()) {
            if (countryName.value == value)
                return countryName;
        }
        throw new IllegalArgumentException("No country found by this value!");
    }
}
