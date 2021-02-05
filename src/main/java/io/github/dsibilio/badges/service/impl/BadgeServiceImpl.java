package io.github.dsibilio.badges.service.impl;

import org.springframework.stereotype.Service;

import io.github.dsibilio.badgemaker.core.BadgeFormatBuilder;
import io.github.dsibilio.badgemaker.core.BadgeMaker;
import io.github.dsibilio.badgemaker.model.BadgeFormat;
import io.github.dsibilio.badges.model.BadgeType;
import io.github.dsibilio.badges.service.api.BadgeService;
import io.github.dsibilio.badges.web.client.api.SonarWebClient;
import io.github.dsibilio.badges.web.server.model.NamedColor;
import io.github.dsibilio.models.Condition;
import io.github.dsibilio.models.ProjectStatus.Status;
import io.github.dsibilio.models.QualityGate;
import reactor.core.publisher.Mono;

@Service
public class BadgeServiceImpl implements BadgeService {

  private static final String SONAR_LOGO = "data:image/svg+xml;base64,PHN2ZyBmaWxsPSIjNEU5QkNEIiByb2xlPSJpbWciIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgdmlld0JveD0iMCAwIDI0IDI0Ij48dGl0bGU+U29uYXJRdWJlIGljb248L3RpdGxlPjxwYXRoIGQ9Ik0xNS42ODUuMzg2bC0uNDY1Ljc2NmMzLjQ3NyAyLjExMiA2LjMwNSA1LjI3IDcuOTY2IDguODlMMjQgOS42N0MyMi4yNjYgNS44ODcgMTkuMzEzIDIuNTkgMTUuNjg1LjM4NnpNOC40NjIuOTFsLS4zMDUgMS4wNzVjNi44OSAxLjk3NiAxMi4zODQgNy42NCAxMy45OTcgMTQuNDIxbDEuMDg1LS4yNThDMjEuNTM1IDguOTc3IDE1LjczNSAyLjk5NyA4LjQ2Mi45MDl6TTAgMi42Njd2MS4zNDJjMTAuOTYzIDAgMTkuODgzIDguNzk1IDE5Ljg4MyAxOS42MDVoMS4zNDJjMC0xMS41NS05LjUyMi0yMC45NDctMjEuMjI1LTIwLjk0N3oiLz48L3N2Zz4=";
  private final SonarWebClient sonarWebClient;

  public BadgeServiceImpl(SonarWebClient sonarWebClient) {
    this.sonarWebClient = sonarWebClient;
  }

  @Override
  public Mono<String> getBadge(String message, String label, NamedColor messageColor, NamedColor labelColor,
      String logo) {
    BadgeFormat badgeFormat = new BadgeFormatBuilder(message).withLabel(label)
        .withLabelColor(toNamedColor(labelColor, io.github.dsibilio.badgemaker.model.NamedColor.GREY))
        .withMessageColor(toNamedColor(messageColor, io.github.dsibilio.badgemaker.model.NamedColor.BRIGHTGREEN))
        .withLogo(logo).build();

    return Mono.fromCallable(() -> BadgeMaker.makeBadge(badgeFormat));
  }

  @Override
  public Mono<String> getQualityBadge(String projectKey, String label, NamedColor labelColor, String logo) {
    return sonarWebClient.getQualityGate(projectKey)
        .map(qualityGate -> buildSonarBadge(qualityGate, label, labelColor, logo, BadgeType.QUALITY));
  }

  @Override
  public Mono<String> getCoverageBadge(String projectKey, String label, NamedColor labelColor, String logo) {
    return sonarWebClient.getQualityGate(projectKey)
        .map(qualityGate -> buildSonarBadge(qualityGate, label, labelColor, logo, BadgeType.COVERAGE));
  }

  private static io.github.dsibilio.badgemaker.model.NamedColor toNamedColor(NamedColor color,
      io.github.dsibilio.badgemaker.model.NamedColor defaultColor) {
    if (color != null) {
      return io.github.dsibilio.badgemaker.model.NamedColor.valueOf(color.toString());
    }

    return defaultColor;
  }

  private static String buildSonarBadge(QualityGate qualityGate, String label, NamedColor labelColor, String logo,
      BadgeType badgeType) {
    String message;
    boolean isOk;

    if (badgeType.equals(BadgeType.QUALITY)) {
      isOk = qualityGate.getProjectStatus().getStatus().equals(Status.OK);
      message = isOk ? "passed" : "failed";
    } else {
      Condition coverage = qualityGate.getProjectStatus().getConditions().stream()
          .filter(condition -> condition.getMetricKey().equalsIgnoreCase("coverage")).findFirst().orElseThrow();
      message = coverage.getActualValue() + "%";
      isOk = Double.valueOf(coverage.getActualValue()) >= Double.valueOf(coverage.getErrorThreshold());
    }

    BadgeFormat badgeFormat = new BadgeFormatBuilder(message).withLabel(label != null ? label : badgeType.getLabel())
        .withLabelColor(toNamedColor(labelColor, io.github.dsibilio.badgemaker.model.NamedColor.GREY))
        .withMessageColor(getBadgeColor(isOk)).withLogo(logo != null ? logo : SONAR_LOGO).build();

    return BadgeMaker.makeBadge(badgeFormat);
  }

  private static io.github.dsibilio.badgemaker.model.NamedColor getBadgeColor(boolean isOk) {
    return isOk ? io.github.dsibilio.badgemaker.model.NamedColor.BRIGHTGREEN
        : io.github.dsibilio.badgemaker.model.NamedColor.RED;
  }

}