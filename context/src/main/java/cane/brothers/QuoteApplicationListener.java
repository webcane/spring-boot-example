package cane.brothers;

import cane.brothers.quote.TerminatorQuote;
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
