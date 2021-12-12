package cane.brothers.spring;

import cane.brothers.spring.quote.Quote;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by cane
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Quote quote = context.getBean(Quote.class);
        quote.sayQuote();
    }
}
