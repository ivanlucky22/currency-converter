package com.example.converterservice.exception;

public class ExternalConverterException extends RuntimeException {
    public ExternalConverterException(final String message, final Exception e) {
        super(message, e);
    }
}
