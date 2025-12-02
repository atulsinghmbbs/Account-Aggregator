package com.delcapital.controller;

import com.delcapital.dto.request.FiRequestDTO;
import com.delcapital.dto.response.FiResponseDTO;
import com.delcapital.service.FiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/v1/fi")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FiController {

    private final FiService fiRequestService;

    @PostMapping("/request/{consentRequestId}")
    public ResponseEntity<FiResponseDTO> createFiRequest(@PathVariable String consentRequestId, @Valid @RequestBody FiRequestDTO request) {
        log.info("Creating FI request for consent: {}", consentRequestId);
        FiResponseDTO response = fiRequestService.createFiRequest(consentRequestId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/request/{fiRequestId}/details")
    public ResponseEntity<FiResponseDTO> getFiRequestDetails(@PathVariable String fiRequestId) {
        log.info("Fetching FI request details for: {}", fiRequestId);
        FiResponseDTO response = fiRequestService.getFiRequestDetails(fiRequestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consent/{consentRequestId}/data")
    public ResponseEntity<FiResponseDTO> getFiDataByConsentId(@PathVariable String consentRequestId) {
        log.info("Fetching FI data for consent: {}", consentRequestId);
        FiResponseDTO response = fiRequestService.getFiDataByConsentId(consentRequestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consent/{consentRequestId}/requests")
    public ResponseEntity<List<FiResponseDTO>> getFiRequestsByConsent(@PathVariable String consentRequestId) {
        log.info("Fetching all FI requests for consent: {}", consentRequestId);
        List<FiResponseDTO> responses = fiRequestService.getFiRequestsByConsent(consentRequestId);
        return ResponseEntity.ok(responses);
    }
}