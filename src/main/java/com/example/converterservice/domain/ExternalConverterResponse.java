package com.example.converterservice.domain;

import java.math.BigDecimal;
import java.util.Map;

public class ExternalConverterResponse {
    private String base;
    private Map<String, BigDecimal> rates;

    public String getBase() {
        return base;
    }

    public void setBase(final String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(final Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
