package com.exobank.wallet.controller;

import com.exobank.wallet.dto.NibssWebhookPayload;
import com.exobank.wallet.service.NibssWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook/nibss")
@RequiredArgsConstructor
public class NibssWebhookController {

    private final NibssWebhookService nibssWebhookService;

    @PostMapping("/transfer-status")
    public ResponseEntity<String> handleTransferStatus(@RequestBody NibssWebhookPayload payload) {
        nibssWebhookService.processWebhook(payload);
        return ResponseEntity.ok("Received");
    }
}
