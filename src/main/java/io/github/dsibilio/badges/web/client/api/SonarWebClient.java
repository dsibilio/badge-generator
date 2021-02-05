package io.github.dsibilio.badges.web.client.api;

import io.github.dsibilio.models.QualityGate;
import reactor.core.publisher.Mono;

public interface SonarWebClient {

  Mono<QualityGate> getQualityGate(String projectKey);

}
