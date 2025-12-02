package com.delcapital.service;

import com.delcapital.dto.request.ConsentRequestDTO;
import com.delcapital.dto.response.ConsentResponseDTO;

import java.util.List;

public interface ConsentService {
    public ConsentResponseDTO createConsentRequest(ConsentRequestDTO requestDTO);
    public ConsentResponseDTO getConsentDetails(String consentRequestId);
    public List<ConsentResponseDTO> getAllConsents();
    public List<ConsentResponseDTO> getConsentsByCustomer(String customerIdentifier);
}
