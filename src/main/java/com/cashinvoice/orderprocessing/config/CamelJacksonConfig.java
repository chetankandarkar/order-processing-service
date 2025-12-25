package com.cashinvoice.orderprocessing.config;

import com.cashinvoice.orderprocessing.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelJacksonConfig {

    private final ObjectMapper objectMapper;

    public CamelJacksonConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public JacksonDataFormat orderDataFormat() {
        return new JacksonDataFormat(objectMapper, Order.class);
    }
}
