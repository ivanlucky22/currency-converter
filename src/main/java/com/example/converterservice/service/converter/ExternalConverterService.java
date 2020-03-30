package com.example.converterservice.service.converter;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ConverterResponse;
import reactor.core.publisher.Mono;

public interface ExternalConverterService {

    Mono<ConverterResponse> convert(ConverterRequest request);

    String getUrl();
}
