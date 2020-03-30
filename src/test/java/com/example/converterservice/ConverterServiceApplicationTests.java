package com.example.converterservice;

import com.example.converterservice.domain.ConverterRequest;
import com.example.converterservice.domain.ExternalConverterResponse;
import com.example.converterservice.service.RequestService;
import com.example.converterservice.web.rest.ConverterResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ConverterServiceApplicationTests {

    @Autowired
    private ConverterResource converterResource;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private RequestService requestService;

    private MockMvc restUserMockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(converterResource)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Test
    void testConvertsEurToUsd() throws Exception {
        final ConverterRequest converterRequest = createRequest("EUR", "USD", "2");

        final ExternalConverterResponse externalConverterResponse = mockExternalResponse("EUR", "USD", "1.1");

        doReturn(Mono.just(externalConverterResponse)).when(requestService).request(any(), any());

        final ResultActions resultActions = restUserMockMvc.
                perform(post("/currency/convert")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsBytes(converterRequest)));
        restUserMockMvc.
                perform(asyncDispatch(resultActions.andReturn())) // because MockMvc did not wait for the asynchronous process to finish
                .andExpect(status().isOk())
                .andExpect(jsonPath("converted").value(2.2));
    }

    private ConverterRequest createRequest(final String from, final String to, final String amount) {
        final ConverterRequest converterRequest = new ConverterRequest();
        converterRequest.setFrom(from);
        converterRequest.setTo(to);
        converterRequest.setAmount(new BigDecimal(amount));
        return converterRequest;
    }

    private ExternalConverterResponse mockExternalResponse(final String baseCurrency, final String targetCurrency, final String rate) {
        final ExternalConverterResponse externalConverterResponse = new ExternalConverterResponse();
        externalConverterResponse.setBase(baseCurrency);
        final HashMap<String, BigDecimal> rates = new HashMap<>();
        rates.put(targetCurrency, new BigDecimal(rate));
        externalConverterResponse.setRates(rates);
        return externalConverterResponse;
    }

}
