package com.delcapital.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentRequestDTO {

    @Valid
    @NotNull(message = "Customer details are required")
    @JsonProperty("customer_details")
    private CustomerDetails customerDetails;

    @JsonProperty("customer_id")
    private String customerId;

    @NotBlank(message = "Template ID is required")
    @JsonProperty("template_id")
    private String templateId;

    @Valid
    @NotNull(message = "Consent details are required")
    @JsonProperty("consent_details")
    private ConsentDetails consentDetails;

    @JsonProperty("customer_notification_mode")
    private String customerNotificationMode;

    @JsonProperty("notify_customer")
    private Boolean notifyCustomer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerDetails {

        @NotBlank(message = "Customer name is required")
        @JsonProperty("customer_name")
        private String customerName;

        @Email(message = "Invalid email format")
        @JsonProperty("customer_email")
        private String customerEmail;

        @JsonProperty("customer_ref_id")
        private String customerRefId;

        @NotBlank(message = "Customer mobile is required")
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
        @JsonProperty("customer_mobile")
        private String customerMobile;

        @NotBlank(message = "Customer identifier is required")
        @JsonProperty("customer_identifier")
        private String customerIdentifier;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsentDetails {

        @NotNull(message = "Consent start date is required")
        @JsonProperty("consent_start_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime consentStartDate;

        @NotNull(message = "Consent expiry date is required")
        @JsonProperty("consent_expiry_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime consentExpiryDate;

        @NotNull(message = "FI start date is required")
        @JsonProperty("fi_start_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime fiStartDate;

        @NotNull(message = "FI end date is required")
        @JsonProperty("fi_end_date")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime fiEndDate;

        @JsonProperty("meta")
        private ConsentMeta meta;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsentMeta {

        @JsonProperty("fip_ids")
        private List<String> fipIds;

        @JsonProperty("email_id")
        private String emailId;

        @JsonProperty("dob")
        private String dob;

        @JsonProperty("show_consent_info")
        private Boolean showConsentInfo;

    }
}
