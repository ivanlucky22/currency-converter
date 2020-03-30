package com.example.converterservice.service.impl;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ConverterResponse;
import com.example.converterservice.service.ConverterService;
import com.example.converterservice.service.converter.ExternalConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ConverterServiceImpl implements ConverterService {

    private final Logger log = LoggerFactory.getLogger(ConverterServiceImpl.class);

    private final List<ExternalConverterService> externalConverters;
    private final Random random;

    public ConverterServiceImpl(final List<ExternalConverterService> externalConverters,
                                final Random random) {
        this.externalConverters = externalConverters;
        this.random = random;
    }

    @Override
    public Mono<ConverterResponse> convert(final ConverterRequest converterRequest) {
        Assert.notEmpty(externalConverters, "No external providers available");
        return convert(converterRequest, new ArrayList<>(externalConverters));
    }

    private Mono<ConverterResponse> convert(final ConverterRequest converterRequest, final List<ExternalConverterService> externalConverters) {
        final ExternalConverterService externalConverter = getRandomConverter(externalConverters);
        return externalConverter.convert(converterRequest)
                .onErrorResume(e -> {
                    log.warn(String.format("An Error for external converter %s happened. Reason:%s", externalConverter.getUrl(), e.getMessage()));
                    externalConverters.remove(externalConverter);
                    return convert(converterRequest, externalConverters);
                });
    }

    private ExternalConverterService getRandomConverter(final List<ExternalConverterService> externalConverters) {
        Assert.notEmpty(externalConverters, "All external providers are unreachable at the moment");
        final int index = random.nextInt(externalConverters.size());
        return externalConverters.get(index);
    }
}
