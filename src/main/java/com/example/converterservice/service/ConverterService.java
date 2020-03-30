package com.example.converterservice.service;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ConverterResponse;
import reactor.core.publisher.Mono;

public interface ConverterService {
    Mono<ConverterResponse> convert(ConverterRequest converterRequest);
}
