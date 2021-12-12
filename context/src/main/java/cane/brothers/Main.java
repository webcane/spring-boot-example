package cane.brothers;

import cane.brothers.quote.IQuote;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by cane
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        IQuote quote = context.getBean(IQuote.class);
        quote.sayQuote();
    }
}
