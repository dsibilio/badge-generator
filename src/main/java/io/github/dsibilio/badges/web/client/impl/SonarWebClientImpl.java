package io.github.dsibilio.badges.web.client.impl;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.dsibilio.badges.web.client.api.SonarWebClient;
import io.github.dsibilio.models.QualityGate;
import reactor.core.publisher.Mono;

@Component
public class SonarWebClientImpl implements SonarWebClient {

  private static final Logger LOG = LoggerFactory.getLogger(SonarWebClientImpl.class);
  private final WebClient webClient;
  private final Cache<String, QualityGate> cache;

  public SonarWebClientImpl(WebClient webClient) {
    this.webClient = webClient;
    cache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(10)).build();
  }

  @Override
  public Mono<QualityGate> getQualityGate(String projectKey) {
    QualityGate cachedValue = cache.getIfPresent(projectKey);
    if (cachedValue != null) {
      return Mono.just(cachedValue);
    }

    LOG.info("Requesting quality gate for projectKey={}", projectKey);
    return webClient.get().uri("/api/qualitygates/project_status?projectKey={projectKey}", projectKey)
        .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(QualityGate.class)
        .doOnNext(qualityGate -> cache.put(projectKey, qualityGate));
  }

}
