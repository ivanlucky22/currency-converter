package com.example.converterservice.web.rest;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ConverterResponse;
import com.example.converterservice.service.ConverterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConverterResource {

    private final ConverterService converterService;

    public ConverterResource(final ConverterService converterService) {
        this.converterService = converterService;
    }

    @PostMapping(value = "/currency/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ConverterResponse> convert(@RequestBody ConverterRequest converterRequest) {
        return converterService.convert(converterRequest);
    }
}
