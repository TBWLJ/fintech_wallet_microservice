package com.exobank.wallet.mapper;

import com.exobank.wallet.dto.WalletResponse;
import com.exobank.wallet.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletResponse toDto(Wallet wallet) {
        WalletResponse dto = new WalletResponse();
        dto.setId(wallet.getId());
        dto.setUserId(wallet.getUserId());
        dto.setBalance(wallet.getBalance());
        return dto;
    }
}
