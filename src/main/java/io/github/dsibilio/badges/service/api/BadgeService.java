package io.github.dsibilio.badges.service.api;

import io.github.dsibilio.badges.web.server.model.NamedColor;
import reactor.core.publisher.Mono;

public interface BadgeService {

  Mono<String> getBadge(String message, String label, NamedColor messageColor, NamedColor labelColor, String logo);

  Mono<String> getQualityBadge(String projectKey, String label, NamedColor labelColor, String logo);

  Mono<String> getCoverageBadge(String projectKey, String label, NamedColor labelColor, String logo);

}
