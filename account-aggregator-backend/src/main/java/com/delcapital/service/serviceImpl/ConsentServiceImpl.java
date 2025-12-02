package com.delcapital.service.serviceImpl;



import com.delcapital.Util.DigioClientUtil;
import com.delcapital.dto.request.ConsentRequestDTO;
import com.delcapital.dto.response.ConsentResponseDTO;
import com.delcapital.entity.Consent;
import com.delcapital.enumeration.ConsentStatus;
import com.delcapital.exception.ResourceNotFoundException;
import com.delcapital.repository.ConsentRepository;
import com.delcapital.service.AuditService;
import com.delcapital.service.ConsentService;
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
public class ConsentServiceImpl implements ConsentService {

    private final ConsentRepository consentRepository;
    private final DigioClientUtil digioClientUtil;
    private final ObjectMapper objectMapper;
    private final AuditService auditService;


    @Transactional
    public ConsentResponseDTO createConsentRequest(ConsentRequestDTO requestDTO) {
        log.info("Creating consent request for customer: {}",
                requestDTO.getCustomerDetails().getCustomerIdentifier());


        ConsentResponseDTO response = digioClientUtil.createConsentRequest(requestDTO);


        Consent consentRequest = mapToEntity(requestDTO, response);
        consentRepository.save(consentRequest);

        auditService.logAction("CONSENT_REQUEST", response.getConsentRequestId(),
                "CREATED", "Consent request created successfully");

        log.info("Consent request saved: {}", response.getConsentRequestId());
        return response;
    }

    @Transactional(readOnly = true)
    public ConsentResponseDTO getConsentDetails(String consentRequestId) {
        log.info("Fetching consent details for: {}", consentRequestId);

        ConsentResponseDTO response = digioClientUtil.getConsentDetails(consentRequestId);

        Consent consentRequest = consentRepository
                .findByConsentRequestId(consentRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consent request not found: " + consentRequestId));

        consentRequest.setStatus(ConsentStatus.valueOf(response.getStatus()));
        consentRepository.save(consentRequest);

        return response;
    }

    @Transactional(readOnly = true)
    public List<ConsentResponseDTO> getAllConsents() {
        log.info("Fetching all consent requests");

        return consentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConsentResponseDTO> getConsentsByCustomer(String customerIdentifier) {
        log.info("Fetching consents for customer: {}", customerIdentifier);

        return consentRepository.findByCustomerIdentifier(customerIdentifier).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private Consent mapToEntity(ConsentRequestDTO dto, ConsentResponseDTO response) {
        try {
            return Consent.builder()
                    .consentRequestId(response.getConsentRequestId())
                    .customerName(dto.getCustomerDetails().getCustomerName())
                    .customerEmail(dto.getCustomerDetails().getCustomerEmail())
                    .customerMobile(dto.getCustomerDetails().getCustomerMobile())
                    .customerIdentifier(dto.getCustomerDetails().getCustomerIdentifier())
                    .customerRefId(dto.getCustomerDetails().getCustomerRefId())
                    .templateId(dto.getTemplateId())
                    .status(ConsentStatus.valueOf(response.getStatus()))
                    .consentStartDate(dto.getConsentDetails().getConsentStartDate())
                    .consentExpiryDate(dto.getConsentDetails().getConsentExpiryDate())
                    .fiStartDate(dto.getConsentDetails().getFiStartDate())
                    .fiEndDate(dto.getConsentDetails().getFiEndDate())
                    .metaData(objectMapper.writeValueAsString(dto.getConsentDetails().getMeta()))
                    .gatewayTokenId(response.getGatewayTokenId())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing metadata", e);
        }
    }

    private ConsentResponseDTO mapToResponseDTO(Consent entity) {
        return ConsentResponseDTO.builder()
                .consentRequestId(entity.getConsentRequestId())
                .customerDetails(ConsentResponseDTO.CustomerDetails.builder()
                        .customerName(entity.getCustomerName())
                        .customerEmail(entity.getCustomerEmail())
                        .customerMobile(entity.getCustomerMobile())
                        .customerIdentifier(entity.getCustomerIdentifier())
                        .build())
                .status(entity.getStatus().name())
                .consentDetails(ConsentResponseDTO.ConsentDetails.builder()
                        .consentStartDate(entity.getConsentStartDate())
                        .consentExpiryDate(entity.getConsentExpiryDate())
                        .fiStartDate(entity.getFiStartDate())
                        .fiEndDate(entity.getFiEndDate())
                        .build())
                .gatewayTokenId(entity.getGatewayTokenId())
                .build();
    }

}
