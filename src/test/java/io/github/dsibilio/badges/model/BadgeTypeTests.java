package io.github.dsibilio.badges.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BadgeTypeTests {
  
  @Test
  void qualityBadgeShouldHaveItsOwnLabel() {
    assertEquals("Quality", BadgeType.QUALITY.getLabel());
  }

  @Test
  void coverageBadgeShouldHaveItsOwnLabel() {
    assertEquals("Coverage", BadgeType.COVERAGE.getLabel());
  }

}
