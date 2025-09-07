package com.example.demo.config;

// Mapper Feature

import com.fasterxml.jackson.databind.MapperFeature;
// Spring Boot Hook for global ObjectMapper
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class JacksonEnum {
    @Bean
    // return a function that enables a Jackson feature on a shared mapper.
    public Jackson2ObjectMapperBuilderCustomizer caseInsensitiveEnums() {
        return builder -> builder.featuresToEnable(
                // "pending" "Pending" "PENDING" etc. all map to enum constant PENDING and applies app-wide to all enums deserialized from JSON.
                MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS

        );
    }
}
