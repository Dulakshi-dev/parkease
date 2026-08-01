package com.parkease.reservationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RestClientConfig {

	@Value("${driver.service.url}")
	private String driverServiceUrl;
	
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(driverServiceUrl)
                .build();
    }
}