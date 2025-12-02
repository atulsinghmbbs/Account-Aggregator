package com.delcapital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    public static final String FAILURE = "Failure";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponse> resourceNotFoundHandler(ResourceNotFoundException rex, WebRequest wr) {

        APIErrorResponse response = new APIErrorResponse();
        response.setMessage(rex.getMessage());
        response.setDescription(wr.getDescription(false));
        response.setStatus(FAILURE);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DigioApiException.class)
    public ResponseEntity<APIErrorResponse> resourceNotFoundHandler(DigioApiException rex, WebRequest wr) {

        APIErrorResponse response = new APIErrorResponse();
        response.setMessage(rex.getMessage());
        response.setDescription(wr.getDescription(false));
        response.setStatus(FAILURE);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
