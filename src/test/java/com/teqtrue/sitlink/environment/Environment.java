package com.teqtrue.sitlink.environment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;

public class Environment {

  private static ObjectMapper objectMapper;

  public static ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    return objectMapper;
  }

  public static HttpMessageConverter<?> createDefaultMessageConverter() {
    return new MappingJackson2HttpMessageConverter(getObjectMapper());
  }

  public static HttpMessageConverter<?> createStringEncodingMessageConverter() {
    return new StringHttpMessageConverter(StandardCharsets.UTF_8);
  }
}
