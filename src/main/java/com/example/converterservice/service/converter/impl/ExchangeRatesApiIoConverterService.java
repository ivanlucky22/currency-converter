package com.example.converterservice.service.converter.impl;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.service.converter.AbstractExternalConverterService;
import com.example.converterservice.service.RequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ExchangeRatesApiIoConverterService extends AbstractExternalConverterService {

    public ExchangeRatesApiIoConverterService(@Value("${converters.url2}") final String url,
                                              final RequestService requestService) {
        super(url, requestService);
    }

    @Override
    protected URI getRequestUri(final ConverterRequest request) {
        return UriComponentsBuilder.newInstance()
                .uri(URI.create(getUrl()))
                .queryParam("base", request.getFrom())
                .build().toUri();
    }
}
