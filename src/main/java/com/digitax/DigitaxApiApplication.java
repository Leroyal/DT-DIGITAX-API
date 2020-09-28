package com.digitax;



import com.digitax.app.properties.AppProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
@EnableConfigurationProperties(AppProperty.class)
public class DigitaxApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DigitaxApiApplication.class, args);
    }
    
}