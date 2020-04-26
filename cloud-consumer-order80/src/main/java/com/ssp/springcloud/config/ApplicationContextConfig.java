package com.ssp.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    //依赖注入
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
