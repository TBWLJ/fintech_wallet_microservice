package com.exobank.wallet.integration;

import java.math.BigDecimal;

public interface NibssService {
    boolean transferToBank(String bankCode, String accountNumber, String accountName, BigDecimal amount, String reference, String narration);
}
