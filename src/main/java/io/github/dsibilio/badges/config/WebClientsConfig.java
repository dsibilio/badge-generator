package io.github.dsibilio.badges.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(SonarProperties.class)
public class WebClientsConfig {

  private final SonarProperties sonarProperties;

  public WebClientsConfig(SonarProperties sonarProperties) {
    this.sonarProperties = sonarProperties;
  }

  @Bean
  public WebClient sonarWebClient() {
    return WebClient.builder().baseUrl(sonarProperties.getUri())
        .filter(
            ExchangeFilterFunctions.basicAuthentication(sonarProperties.getUsername(), sonarProperties.getPassword()))
        .build();
  }

}
