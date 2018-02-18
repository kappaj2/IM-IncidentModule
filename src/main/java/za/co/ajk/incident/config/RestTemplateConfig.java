package za.co.ajk.incident.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import za.co.ajk.incident.security.RestTemplateInterceptor;


@Configuration
public class RestTemplateConfig {
    
    @Bean
    @LoadBalanced
    @RefreshScope
    public RestTemplate restTemplate(RestTemplateBuilder builder, ApplicationProperties props) {
        builder.setConnectTimeout(props.getRestTemplateConfig().getConnectTimeout());
        builder.setReadTimeout(props.getRestTemplateConfig().getReadTimeout());
    
        List<ClientHttpRequestInterceptor> addInterceptors = new ArrayList<>();
        addInterceptors.add(new RestTemplateInterceptor());
        
        RestTemplate restTemplate = builder.build();
        restTemplate.getInterceptors().addAll(addInterceptors);
        return restTemplate;
    }
}
//http://springinpractice.com/2013/10/27/how-to-send-an-http-header-with-every-request-with-spring-resttemplate
