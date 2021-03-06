package com.example.converterservice.service.converter.impl;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.service.converter.AbstractExternalConverterService;
import com.example.converterservice.service.RequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class ExchangeRateApiComConverterService extends AbstractExternalConverterService {

    public ExchangeRateApiComConverterService(@Value("${converters.url1}") final String url,
                                              final RequestService requestService) {
        super(url, requestService);
    }

    @Override
    protected URI getRequestUri(final ConverterRequest request) {
        return URI.create(getUrl() + "/" + request.getFrom());
    }
}
