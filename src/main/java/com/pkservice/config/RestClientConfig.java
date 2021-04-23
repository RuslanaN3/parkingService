package com.pkservice.config;

import com.pkservice.restClient.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RestClientConfig {

    @Bean
    public RestClient getRestClient() {
        return new RestClient();
    }

}
