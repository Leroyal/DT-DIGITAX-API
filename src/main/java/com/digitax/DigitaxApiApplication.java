package com.digitax;



import com.digitax.app.properties.AppProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(AppProperty.class)
@EnableTransactionManagement
public class DigitaxApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DigitaxApiApplication.class, args);
    }
    
    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
   
    
}