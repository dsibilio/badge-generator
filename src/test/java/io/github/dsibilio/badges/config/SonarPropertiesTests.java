package io.github.dsibilio.badges.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SonarPropertiesTests {

  @Autowired
  private SonarProperties sonarProperties;

  @Test
  void propertiesShouldBeSet() {
    assertNotNull(sonarProperties.getUsername());
    assertNotNull(sonarProperties.getPassword());
    assertNotNull(sonarProperties.getUri());
  }

}
