package io.github.dsibilio.badges.web.server;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.github.dsibilio.badges.service.api.BadgeService;
import io.github.dsibilio.badges.web.server.api.BadgesApiDelegate;
import io.github.dsibilio.badges.web.server.model.NamedColor;
import reactor.core.publisher.Mono;

@Component
public class BadgesDelegateImpl implements BadgesApiDelegate {

  private final BadgeService badgeService;

  public BadgesDelegateImpl(BadgeService badgeService) {
    this.badgeService = badgeService;
  }

  @Override
  public Mono<ResponseEntity<Resource>> getBadge(String message, String label, NamedColor messageColor,
      NamedColor labelColor, String logo, ServerWebExchange exchange) {
    return withErrorHandling(
        badgeService.getBadge(message, label, messageColor, labelColor, logo).map(this::getResponse));
  }

  @Override
  public Mono<ResponseEntity<Resource>> getSonarQualityBadge(String projectKey, String label, NamedColor labelColor,
      String logo, ServerWebExchange exchange) {
    return withErrorHandling(badgeService.getQualityBadge(projectKey, label, labelColor, logo).map(this::getResponse));
  }

  @Override
  public Mono<ResponseEntity<Resource>> getSonarCoverageBadge(String projectKey, String label, NamedColor labelColor,
      String logo, ServerWebExchange exchange) {
    return withErrorHandling(badgeService.getCoverageBadge(projectKey, label, labelColor, logo).map(this::getResponse));
  }

  private ResponseEntity<Resource> getResponse(String badge) {
    return ResponseEntity.ok().body(new ByteArrayResource(badge.getBytes()));
  }

  private static Mono<ResponseEntity<Resource>> withErrorHandling(Mono<ResponseEntity<Resource>> mono) {
    return mono.onErrorResume(Exception.class, BadgesDelegateImpl::getErrorView);
  }

  private static Mono<ResponseEntity<Resource>> getErrorView(Exception ex) {
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN)
        .body(new ByteArrayResource(ex.getMessage().getBytes())));
  }

}