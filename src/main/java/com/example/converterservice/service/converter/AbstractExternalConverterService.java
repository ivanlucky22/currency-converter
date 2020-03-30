package com.example.converterservice.service.converter;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ConverterResponse;
import com.example.converterservice.service.RequestService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;

public abstract class AbstractExternalConverterService implements ExternalConverterService {

    private final String url;
    private final WebClient client;
    private final RequestService requestService;

    public AbstractExternalConverterService(final String url, final RequestService requestService) {
        this.url = url;
        client = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                //    .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))  Bonus task for Auth
                .build();
        this.requestService = requestService;
    }

    @Override
    public Mono<ConverterResponse> convert(final ConverterRequest request) {
        return requestService.request(client, getRequestUri(request))
                .map(externalConverterResponse -> {
                    final BigDecimal rate = externalConverterResponse.getRates().get(request.getTo());
                    final BigDecimal converted = request.getAmount().multiply(rate).setScale(2, RoundingMode.HALF_DOWN);
                    return new ConverterResponse(request, converted);
                });

    }

    @Override
    public String getUrl() {
        return url;
    }

    abstract protected URI getRequestUri(final ConverterRequest request);
}
