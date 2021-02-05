# Badge Generator
![Build Status](https://github.com/dsibilio/badge-generator/workflows/build/badge.svg) [![Code Coverage](https://codecov.io/github/dsibilio/badge-generator/coverage.svg?branch=main)](https://codecov.io/github/dsibilio/badge-generator?branch=main)


`badge-generator` is a **reactive SVG badge generator** able to produce both static badges and dynamically rendered quality/coverage badges from a private _Sonar_ server.

## Configuration

If you intend to generate dynamic Sonar badges, you must include the following properties in the [application.yml](https://github.com/dsibilio/badge-generator/blob/main/src/main/resources/config/application.yml) file:

```yaml
sonar:
  username: user
  password: password
  uri: https://definitely-my-sonar-server.com
```

## Usage

Run the `badge-generator` with `mvnw spring-boot:run`.
Once the application is up and running, you can query the APIs specified in the [openapi.yaml specification file](https://github.com/dsibilio/badge-generator/blob/main/src/main/resources/openapi/openapi.yaml) in order to generate static/dynamic badges.

## Examples

### Generating a Static Badge

`http://localhost:8080/badges/my-message?label=my-label&labelColor=GREY&messageColor=BLUE`

### Generating a Dynamic Sonar Quality Badge

`http://localhost:8080/badges/quality/sonarProjectKey`

### Generating a Dynamic Sonar Coverage Badge

`http://localhost:8080/badges/coverage/sonarProjectKey`

## Testing

Run the Unit Tests with `mvnw verify`. 

Run all tests, including Integration Tests, with `mvn verify -PtestIT`.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/#build-image)
* [Spring cache abstraction](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-caching)

## Guides
The following guides illustrate how to use some features concretely:

* [Caching Data with Spring](https://spring.io/guides/gs/caching/)

