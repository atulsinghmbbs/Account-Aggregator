package com.delcapital.service.serviceImpl;

import com.delcapital.Util.DigioClientUtil;
import com.delcapital.dto.request.FiRequestDTO;
import com.delcapital.dto.response.FiResponseDTO;
import com.delcapital.entity.FiData;
import com.delcapital.entity.FiRequest;
import com.delcapital.enumeration.FiRequestStatus;
import com.delcapital.exception.ResourceNotFoundException;
import com.delcapital.repository.FiDataRepository;
import com.delcapital.repository.FiRequestRepository;
import com.delcapital.service.AuditService;
import com.delcapital.service.FiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FiServiceImpl implements FiService {


    private final FiRequestRepository fiRequestRepository;
    private final FiDataRepository fiDataRepository;
    private final DigioClientUtil digioClientUtil;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Transactional
    public FiResponseDTO createFiRequest(String consentRequestId, FiRequestDTO requestDTO) {
        log.info("Creating FI request for consent: {}", consentRequestId);

        FiResponseDTO response = digioClientUtil.createFiRequest(consentRequestId, requestDTO);

        FiRequest fiRequest = FiRequest.builder()
                .fiRequestId(response.getFiRequestId())
                .consentRequestId(consentRequestId)
                .fiStartDate(requestDTO.getFrom())
                .fiEndDate(requestDTO.getTo())
                .status(FiRequestStatus.valueOf(response.getStatus()))
                .build();

        fiRequestRepository.save(fiRequest);

        auditService.logAction("FI_REQUEST", response.getFiRequestId(),
                "CREATED", "FI request created successfully");

        log.info("FI request saved: {}", response.getFiRequestId());
        return response;
    }

    @Transactional(readOnly = true)
    public FiResponseDTO getFiRequestDetails(String fiRequestId) {
        log.info("Fetching FI request details for: {}", fiRequestId);

        FiResponseDTO response = digioClientUtil.getFiRequestDetails(fiRequestId);

        FiRequest fiRequest = fiRequestRepository.findByFiRequestId(fiRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FI request not found: " + fiRequestId));

        fiRequest.setStatus(FiRequestStatus.valueOf(response.getStatus()));
        fiRequestRepository.save(fiRequest);

        if (response.getFi() != null && !response.getFi().isEmpty()) {
            saveFiData(response);
        }

        return response;
    }

    @Transactional(readOnly = true)
    public FiResponseDTO getFiDataByConsentId(String consentRequestId) {
        log.info("Fetching FI data for consent: {}", consentRequestId);

        FiResponseDTO response = digioClientUtil.getFiDataByConsentId(consentRequestId);

        if (response.getFi() != null && !response.getFi().isEmpty()) {
            saveFiData(response);
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<FiResponseDTO> getFiRequestsByConsent(String consentRequestId) {
        log.info("Fetching all FI requests for consent: {}", consentRequestId);

        return fiRequestRepository.findByConsentRequestId(consentRequestId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private void saveFiData(FiResponseDTO response) {
        try {
            for (FiResponseDTO.FiDataItem item : response.getFi()) {
                FiData fiData = FiData.builder()
                        .fiRequestId(response.getFiRequestId())
                        .accountId(item.getAccountId())
                        .fiDataId(item.getFiDataId())
                        .fipId(item.getFipId())
                        .fiType(item.getFiType())
                        .maskedAccountNumber(item.getAccountNumber())
                        .rawFiData(objectMapper.writeValueAsString(item.getFiData()))
                        .build();

                fiDataRepository.save(fiData);
            }

            log.info("FI data saved for request: {}", response.getFiRequestId());
        } catch (JsonProcessingException e) {
            log.error("Error serializing FI data", e);
        }
    }

    private FiResponseDTO mapToResponseDTO(FiRequest entity) {
        return FiResponseDTO.builder()
                .fiRequestId(entity.getFiRequestId())
                .fiStartDate(entity.getFiStartDate())
                .fiEndDate(entity.getFiEndDate())
                .status(entity.getStatus().name())
                .consentRequestId(entity.getConsentRequestId())
                .build();
    }

}
