package com.delcapital.service;

import com.delcapital.dto.request.FiRequestDTO;
import com.delcapital.dto.response.FiResponseDTO;

import java.util.List;

public interface FiService {
    public FiResponseDTO createFiRequest(String consentRequestId, FiRequestDTO requestDTO);
    public FiResponseDTO getFiRequestDetails(String fiRequestId);
    public FiResponseDTO getFiDataByConsentId(String consentRequestId);
    public List<FiResponseDTO> getFiRequestsByConsent(String consentRequestId);
}
