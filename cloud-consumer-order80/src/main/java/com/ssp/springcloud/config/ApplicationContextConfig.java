package com.ssp.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    //依赖注入
    @Bean
    //@LoadBalanced//负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
