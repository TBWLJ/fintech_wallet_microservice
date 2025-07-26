package com.exobank.wallet.controller;

import com.exobank.wallet.dto.*;
import com.exobank.wallet.model.Wallet;
import com.exobank.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    // 1. Get wallet balance
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getWalletBalance(@RequestParam String userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(wallet.getBalance());
    }

    // 2. Credit wallet (for admin or test purposes)
    @PostMapping("/credit")
    public ResponseEntity<Wallet> creditWallet(@RequestParam String userId,
                                               @RequestParam BigDecimal amount) {
        Wallet wallet = walletService.creditWallet(userId, amount);
        return ResponseEntity.ok(wallet);
    }

    // 3. Debit wallet (admin or test only)
    @PostMapping("/debit")
    public ResponseEntity<Wallet> debitWallet(@RequestParam String userId,
                                              @RequestParam BigDecimal amount) {
        Wallet wallet = walletService.debitWallet(userId, amount);
        return ResponseEntity.ok(wallet);
    }

    // 4. Internal transfer (within your platform)
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferRequest request) {
        walletService.transfer(request);
        return ResponseEntity.ok("Internal transfer successful");
    }

    // 5. External bank transfer (via NIBSS)
    @PostMapping("/transfer/external")
    public ResponseEntity<String> transferToExternalBank(@Valid @RequestBody ExternalTransferRequest request) {
        walletService.transferToExternalBank(request);
        return ResponseEntity.ok("External transfer initiated via NIBSS");
    }

    // 6. Withdraw funds (same as external transfer but tied to withdrawal request)
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody WithdrawRequest request) {
        walletService.withdraw(request);
        return ResponseEntity.ok("Withdrawal request submitted");
    }

    // 7. Get transaction history
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable String userId) {
        List<TransactionResponse> history = walletService.getTransactionHistory(userId);
        return ResponseEntity.ok(history);
    }

    // 8. Check payout status
    @GetMapping("/payout-status/{reference}")
    public ResponseEntity<String> getPayoutStatus(@PathVariable String reference) {
        String status = walletService.getPayoutStatus(reference);
        return ResponseEntity.ok(status);
    }
}
