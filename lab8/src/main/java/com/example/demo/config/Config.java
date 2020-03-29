package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(@Value("${context.path}") final String contextPath,
                                           @Value("${schema.location}") final Resource schemaLocation) {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(contextPath);
        marshaller.setSchemas(schemaLocation);
        marshaller.setMarshallerProperties(Map.of(Marshaller.JAXB_FORMATTED_OUTPUT, true));
        marshaller.setMarshallerProperties(Map.of(Marshaller.JAXB_FORMATTED_OUTPUT, true));

        return marshaller;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
