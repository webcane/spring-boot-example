package cane.brothers.spring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApplication.class)
@WebIntegrationTest
public class BootApplicationTests {
	
	@Test
	public void contextLoads() {
	}
	
// TODO	rest template
//	@Test
//	public void getItems() {
//		RestTemplate rest = new RestTemplate();
//		String resp = rest.getForObject("http://localhost:8090/items", String.class);
//		
//		assertEquals("asd", resp);
//	}

}
