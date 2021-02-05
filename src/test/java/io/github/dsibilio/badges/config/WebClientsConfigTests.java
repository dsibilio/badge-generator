package io.github.dsibilio.badges.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class WebClientsConfigTests {

  @Autowired
  private WebClient webClient;

  @Test
  void sonarWebClientShouldExist() {
    assertNotNull(webClient);
  }

}
