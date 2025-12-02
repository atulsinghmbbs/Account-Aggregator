package com.delcapital.controller;

import com.delcapital.dto.request.ConsentRequestDTO;
import com.delcapital.dto.response.ConsentResponseDTO;
import com.delcapital.service.ConsentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/v1/consent")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ConsentController {

    private final ConsentService consentService;

    @PostMapping("/request")
    public ResponseEntity<ConsentResponseDTO> createConsentRequest(@Valid @RequestBody ConsentRequestDTO request) {
        log.info("Received consent request for customer: {}", request.getCustomerDetails().getCustomerIdentifier());
        ConsentResponseDTO response = consentService.createConsentRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/request/{consentRequestId}/details")
    public ResponseEntity<ConsentResponseDTO> getConsentDetails(@PathVariable String consentRequestId) {
        log.info("Fetching consent details for: {}", consentRequestId);
        ConsentResponseDTO response = consentService.getConsentDetails(consentRequestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ConsentResponseDTO>> getAllConsents() {
        log.info("Fetching all consent requests");
        List<ConsentResponseDTO> responses = consentService.getAllConsents();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{customerIdentifier}")
    public ResponseEntity<List<ConsentResponseDTO>> getConsentsByCustomer(@PathVariable String customerIdentifier) {
        log.info("Fetching consents for customer: {}", customerIdentifier);
        List<ConsentResponseDTO> responses = consentService.getConsentsByCustomer(customerIdentifier);
        return ResponseEntity.ok(responses);
    }
}