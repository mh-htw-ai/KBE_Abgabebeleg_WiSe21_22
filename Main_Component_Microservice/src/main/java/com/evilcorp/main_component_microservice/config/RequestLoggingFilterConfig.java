package com.evilcorp.main_component_microservice.config;

import com.evilcorp.main_component_microservice.logging.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    RequestLoggingFilter filter(){
        RequestLoggingFilter filter = new RequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setBeforeMessagePrefix("Request received: ");
        filter.setAfterMessagePrefix("Request Data: ");
        return  filter;
    }
}