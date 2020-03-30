package com.example.converterservice.service.converter;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ConverterResponse;
import com.example.converterservice.domain.ExternalConverterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;

public abstract class AbstractExternalConverterService implements ExternalConverterService {

    private final Logger log = LoggerFactory.getLogger(AbstractExternalConverterService.class);

    private final String url;
    private final WebClient client;

    public AbstractExternalConverterService(final String url) {
        this.url = url;
        client = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                //    .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))  Bonus task for Auth
                .build();
    }

    @Override
    public Mono<ConverterResponse> convert(final ConverterRequest request) {
        return client.get()
                .uri(uriBuilder -> {
                    final URI requestUri = getRequestUri(uriBuilder, request);
                    log.info("Querying " + requestUri);
                    return requestUri;
                })
                .retrieve()
                .bodyToMono(ExternalConverterResponse.class)
                .map(externalConverterResponse -> {
                    final BigDecimal rate = externalConverterResponse.getRates().get(request.getTo());
                    final BigDecimal converted = request.getAmount().multiply(rate).setScale(2, RoundingMode.DOWN);
                    return new ConverterResponse(request, converted);
                });

    }

    @Override
    public String getUrl() {
        return url;
    }

    abstract protected URI getRequestUri(final UriBuilder uriBuilder, final ConverterRequest request);
}
