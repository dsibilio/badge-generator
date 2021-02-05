package io.github.dsibilio.badges.model;

public enum BadgeType {
  QUALITY("Quality"), COVERAGE("Coverage");

  private final String label;

  private BadgeType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

}
