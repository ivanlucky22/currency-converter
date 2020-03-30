package com.example.converterservice.service.impl;

import com.example.converterservice.domain.ExternalConverterResponse;
import com.example.converterservice.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class RequestServiceImpl implements RequestService {
    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Override
    public Mono<ExternalConverterResponse> request(final WebClient client, final URI uri) {
        log.info("Querying " + uri);
        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ExternalConverterResponse.class);
    }
}
