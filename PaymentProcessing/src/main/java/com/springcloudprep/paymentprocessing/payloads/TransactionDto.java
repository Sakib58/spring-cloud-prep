package com.springcloudprep.paymentprocessing.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {
    private Long userId;
    private double amount;
}
