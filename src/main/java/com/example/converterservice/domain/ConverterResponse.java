package com.example.converterservice.domain;

import java.math.BigDecimal;

public class ConverterResponse {
    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal converted;

    public ConverterResponse() {
    }

    public ConverterResponse(final ConverterRequest request, final BigDecimal converted) {
        if (request != null) {
            this.from = request.getFrom();
            this.to = request.getTo();
            this.amount = request.getAmount();
        }
        this.converted = converted;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConverted() {
        return converted;
    }

    public void setConverted(final BigDecimal converted) {
        this.converted = converted;
    }
}
