package com.delcapital.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FiResponseDTO {

    @JsonProperty("fi_request_id")
    private String fiRequestId;

    @JsonProperty("fi_start_date")
    private LocalDateTime fiStartDate;

    @JsonProperty("fi_end_date")
    private LocalDateTime fiEndDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("consent_request_id")
    private String consentRequestId;

    @JsonProperty("fi")
    private List<FiDataItem> fi;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FiDataItem {
        @JsonProperty("fi_data")
        private Object fiData;

        @JsonProperty("fip_id")
        private String fipId;

        @JsonProperty("fi_type")
        private String fiType;

        @JsonProperty("account_number")
        private String accountNumber;

        @JsonProperty("account_id")
        private String accountId;

        @JsonProperty("fi_data_id")
        private String fiDataId;
    }
}