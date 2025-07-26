package com.exobank.wallet.dto;

import lombok.Data;

@Data
public class NameEnquiryRequest {
    private String accountNumber;
    private String bankCode;
}
