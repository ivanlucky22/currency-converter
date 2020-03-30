package com.example.converterservice.service.converter.impl;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.service.converter.AbstractExternalConverterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

@Service
public class ExchangeRatesApiIoConverterService extends AbstractExternalConverterService {

    public ExchangeRatesApiIoConverterService(@Value("${converters.url2}") final String url) {
        super(url);
    }

    @Override
    protected URI getRequestUri(final UriBuilder uriBuilder, final ConverterRequest request) {
        return uriBuilder.path("/" + request.getFrom()).build();
    }
}
