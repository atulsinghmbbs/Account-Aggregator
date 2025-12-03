package com.delcapital.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // <-- yeh bhi add kar do (safe rahega)
public class ConsentResponseDTO {

    @JsonProperty("consent_request_id")
    private String consentRequestId;

    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;

    @JsonProperty("status")
    private String status;

    @JsonProperty("consent_details")
    private ConsentDetails consentDetails;

    @JsonProperty("gateway_token_id")
    private String gatewayTokenId;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   // <-- yeh bhi add kar do
    private LocalDateTime createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerDetails {
        @JsonProperty("customer_name")
        private String customerName;

        @JsonProperty("customer_email")
        private String customerEmail;

        @JsonProperty("customer_mobile")
        private String customerMobile;

        @JsonProperty("customer_identifier")
        private String customerIdentifier;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsentDetails {

        @JsonProperty("consent_start_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   // <-- YEHI LAGANA THA!
        private LocalDateTime consentStartDate;

        @JsonProperty("consent_expiry_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   // <-- YE BHI
        private LocalDateTime consentExpiryDate;

        @JsonProperty("fi_start_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   // <-- YE BHI
        private LocalDateTime fiStartDate;

        @JsonProperty("fi_end_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   // <-- YE BHI
        private LocalDateTime fiEndDate;
    }
}