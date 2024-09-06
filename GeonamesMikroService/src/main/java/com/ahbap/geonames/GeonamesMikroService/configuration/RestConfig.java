package com.ahbap.geonames.GeonamesMikroService.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    @Scope("prototype")
    public RestTemplate tamplet()
    {
        return new RestTemplate();
    }
}
