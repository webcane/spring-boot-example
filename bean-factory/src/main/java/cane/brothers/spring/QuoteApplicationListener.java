package cane.brothers.spring;

import cane.brothers.spring.quote.TerminatorQuote;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by cane
 */
public class QuoteApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        TerminatorQuote quoteBean = applicationContext.getBean(TerminatorQuote.class);
        quoteBean.setMessage("Hello, World!");
    }
}
