package io.github.dsibilio.badges;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BadgeGeneratorApplicationTests {

	@Autowired
	private BadgeGeneratorApplication app;

	@Test
	void contextLoads() {
		assertNotNull(app);
	}

}
