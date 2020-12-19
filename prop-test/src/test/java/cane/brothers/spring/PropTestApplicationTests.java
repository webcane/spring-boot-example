package cane.brothers.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@SpringBootTest(classes = PropTestApplication.class)
public class PropTestApplicationTests {

	@Autowired
	Environment env;
	
	@Value("${spring.test.message2}")
	private String message2;
	
	@Test
	public void testEnvironment() {
		assertEquals("message1", env.getProperty("spring.test.message1"));
	}
	
	@Test
	public void testValue() {
		assertEquals("message2", message2);
	}

}
