package org.app.serviceusers.management.users.infrastructure.configurations.mapper;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

     @Bean
     public ObjectMapper objectMapper() {
         ObjectMapper objectMapper = new ObjectMapper();
         objectMapper.registerModule(new JavaTimeModule());
         objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
         objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
         return objectMapper;
     }

}