package io.github.dsibilio.badges.web.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import io.github.dsibilio.badges.web.server.api.BadgesApiDelegate;
import reactor.test.StepVerifier;

@SpringBootTest
class BadgesDelegateImplTestIT {

  private static final String FAILED_GATE = "failed-gate";
  private static final String OK_GATE = "ok-gate";
  private static final String CUSTOM_MESSAGE = "custom-message";
  @Autowired
  private BadgesApiDelegate delegate;

  @Test
  void shouldGenerateStaticBadges() {
    StepVerifier.create(delegate.getBadge(CUSTOM_MESSAGE, null, null, null, null, null)).expectNextMatches(response -> {
      try {
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(getResponseBody(response).contains(CUSTOM_MESSAGE));
      } catch (IOException e) {
        fail(e);
      }

      return true;
    }).verifyComplete();
  }

  private String getResponseBody(ResponseEntity<Resource> response) throws IOException {
    return new String(response.getBody().getInputStream().readAllBytes());
  }

  @Test
  void shouldGeneratePassedQualityBadge() {
    StepVerifier.create(delegate.getSonarQualityBadge(OK_GATE, null, null, null, null)).expectNextMatches(response -> {
      try {
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(getResponseBody(response).contains("passed"));
      } catch (IOException e) {
        fail(e);
      }

      return true;
    }).verifyComplete();
  }

  @Test
  void shouldGenerateFailedQualityBadge() {
    StepVerifier.create(delegate.getSonarQualityBadge(FAILED_GATE, null, null, null, null))
        .expectNextMatches(response -> {
          try {
            assertEquals(200, response.getStatusCodeValue());
            assertTrue(getResponseBody(response).contains("failed"));
          } catch (IOException e) {
            fail(e);
          }

          return true;
        }).verifyComplete();
  }

  @Test
  void shouldGenerateOkCoverageBadge() {
    StepVerifier.create(delegate.getSonarCoverageBadge(OK_GATE, null, null, null, null)).expectNextMatches(response -> {
      try {
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(getResponseBody(response).contains("89.4%"));
      } catch (IOException e) {
        fail(e);
      }

      return true;
    }).verifyComplete();
  }

  @Test
  void shouldGenerateFailedCoverageBadge() {
    StepVerifier.create(delegate.getSonarCoverageBadge(FAILED_GATE, null, null, null, null))
        .expectNextMatches(response -> {
          try {
            assertEquals(200, response.getStatusCodeValue());
            assertTrue(getResponseBody(response).contains("79.4%"));
          } catch (IOException e) {
            fail(e);
          }

          return true;
        }).verifyComplete();
  }

}
