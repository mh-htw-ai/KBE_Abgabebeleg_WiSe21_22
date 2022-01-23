package com.evilcorp.main_component_microservice.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingFilterConfig {

    @Bean
    CommonsRequestLoggingFilter filter(){
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();

        filter.setIncludeClientInfo(true);
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);

        filter.setMaxPayloadLength(10000);

        filter.setBeforeMessagePrefix("Received Request: ");
        filter.setAfterMessagePrefix("Request Data: ");

        return  filter;
    }
}
