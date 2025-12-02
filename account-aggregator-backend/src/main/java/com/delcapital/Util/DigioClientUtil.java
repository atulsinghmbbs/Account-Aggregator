package com.delcapital.Util;

import com.delcapital.dto.request.ConsentRequestDTO;
import com.delcapital.dto.request.FiRequestDTO;
import com.delcapital.dto.response.ConsentResponseDTO;
import com.delcapital.dto.response.FiResponseDTO;
import com.delcapital.exception.DigioApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class DigioClientUtil {

    private final RestTemplate restTemplate;

    @Value("${digio.base-url}")
    private String baseUrl;

    @Value("${digio.username}")
    private String username;

    @Value("${digio.password}")
    private String password;

    public DigioClientUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Creates HTTP headers with Basic Authentication
     */
    private HttpHeaders createHeaders() {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }

    /**
     * Create consent request via Digio API
     */
    public ConsentResponseDTO createConsentRequest(ConsentRequestDTO request) {
        log.info("Creating consent request for customer: {}",
                request.getCustomerDetails().getCustomerIdentifier());

        try {
            String url = baseUrl + "/client/consent/request";
            HttpEntity<ConsentRequestDTO> entity = new HttpEntity<>(request, createHeaders());

            ResponseEntity<ConsentResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    ConsentResponseDTO.class
            );

            ConsentResponseDTO responseBody = response.getBody();
            log.info("Consent request created: {}", responseBody.getConsentRequestId());

            return responseBody;

        } catch (HttpClientErrorException e) {
            log.error("Error creating consent request: {}", e.getResponseBodyAsString());
            throw new DigioApiException("Failed to create consent request: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to create consent request", e);
            throw new DigioApiException("Failed to create consent request: " + e.getMessage(), e);
        }
    }

    /**
     * Get consent details by consent request ID
     */
    public ConsentResponseDTO getConsentDetails(String consentRequestId) {
        log.info("Fetching consent details for: {}", consentRequestId);

        try {
            String url = baseUrl + "/client/consent/request/" + consentRequestId + "/details";
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<ConsentResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ConsentResponseDTO.class
            );

            log.info("Consent details fetched: {}", consentRequestId);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("Error fetching consent details: {}", e.getResponseBodyAsString());
            throw new DigioApiException("Failed to fetch consent details: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to fetch consent details", e);
            throw new DigioApiException("Failed to fetch consent details: " + e.getMessage(), e);
        }
    }

    /**
     * Create FI request for a consent
     */
    public FiResponseDTO createFiRequest(String consentRequestId, FiRequestDTO request) {
        log.info("Creating FI request for consent: {}", consentRequestId);

        try {
            String url = baseUrl + "/client/fi/request/" + consentRequestId;
            HttpEntity<FiRequestDTO> entity = new HttpEntity<>(request, createHeaders());

            ResponseEntity<FiResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    FiResponseDTO.class
            );

            FiResponseDTO responseBody = response.getBody();
            log.info("FI request created: {}", responseBody.getFiRequestId());

            return responseBody;

        } catch (HttpClientErrorException e) {
            log.error("Error creating FI request: {}", e.getResponseBodyAsString());
            throw new DigioApiException("Failed to create FI request: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to create FI request", e);
            throw new DigioApiException("Failed to create FI request: " + e.getMessage(), e);
        }
    }

    /**
     * Get FI request details by FI request ID
     */
    public FiResponseDTO getFiRequestDetails(String fiRequestId) {
        log.info("Fetching FI request details for: {}", fiRequestId);

        try {
            String url = baseUrl + "/client/fi/request/" + fiRequestId + "/details";
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<FiResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    FiResponseDTO.class
            );

            log.info("FI request details fetched: {}", fiRequestId);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("Error fetching FI request details: {}", e.getResponseBodyAsString());
            throw new DigioApiException("Failed to fetch FI request details: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to fetch FI request details", e);
            throw new DigioApiException("Failed to fetch FI request details: " + e.getMessage(), e);
        }
    }

    /**
     * Get FI data by consent request ID
     */
    public FiResponseDTO getFiDataByConsentId(String consentRequestId) {
        log.info("Fetching FI data for consent: {}", consentRequestId);

        try {
            String url = baseUrl + "/client/consent/" + consentRequestId + "/data";
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<FiResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    FiResponseDTO.class
            );

            log.info("FI data fetched for consent: {}", consentRequestId);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.error("Error fetching FI data: {}", e.getResponseBodyAsString());
            throw new DigioApiException("Failed to fetch FI data: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to fetch FI data", e);
            throw new DigioApiException("Failed to fetch FI data: " + e.getMessage(), e);
        }
    }
}
