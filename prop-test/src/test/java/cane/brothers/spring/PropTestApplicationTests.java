package cane.brothers.spring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PropTestApplication.class)
@WebAppConfiguration
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
