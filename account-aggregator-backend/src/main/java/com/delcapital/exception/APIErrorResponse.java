package com.delcapital.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIErrorResponse<T> {

    private T message;
    private String description;
    private String status;

    public APIErrorResponse(T message, String status) {
        this.message = message;
        this.status = status;
    }
}

