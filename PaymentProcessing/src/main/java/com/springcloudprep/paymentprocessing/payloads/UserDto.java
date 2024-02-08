package com.springcloudprep.paymentprocessing.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private Integer countryId;
    private Integer currencyId;
}
