package com.delcapital.exception;

public class DigioApiException extends  RuntimeException{

    public DigioApiException() {
    }

    public DigioApiException(String message) {
        super(message);
    }

    public DigioApiException(String s, Exception e) {
    }
}
