package com.example.converterservice.service;

import com.example.converterservice.domain.ExternalConverterResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface RequestService {
    Mono<ExternalConverterResponse> request(WebClient client, URI uri);
}
