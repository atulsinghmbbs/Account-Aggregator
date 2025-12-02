package com.delcapital.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiRequestDTO {

    @NotNull(message = "From date is required")
    @JsonProperty("from")
    private LocalDateTime from;

    @NotNull(message = "To date is required")
    @JsonProperty("to")
    private LocalDateTime to;
}
