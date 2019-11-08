package com.teqtrue.sitlink.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class BeanConfig  {
  
  @Bean
  public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
    return container -> {
      container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    };
  }

  @Bean
  public StrongPasswordEncryptor passEnc() {
    return new StrongPasswordEncryptor();
  }
}
