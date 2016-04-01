package cane.brothers.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Пример как считывать произвольные настройки из конфига приложения.
 * <ul>
 * <li>используя Environment</li>
 * <li>используя аннотацию @Value</li>
 * </ul>
 * 
 * @author cane
 */
@SpringBootApplication
@Controller
public class PropTestApplication {

	@Autowired
	Environment env;
	
	@Value("${spring.test.message2}")
	private String message2;
	
	public static void main(String[] args) {
		SpringApplication.run(PropTestApplication.class, args);
	}
	
	@RequestMapping("/test1")
	@ResponseBody()
    public String home() {
		return env.getProperty("spring.test.message1");
    }
	
	@RequestMapping("/test2")
	@ResponseBody()
    public String test2() {
		return message2;
    }
}
