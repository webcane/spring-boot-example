package cane.brothers;

import cane.brothers.quote.IQuote;
import cane.brothers.quote.TerminatorQuote;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by cane
 */
@Configuration
public class AppConfig {

    @Bean
    public ApplicationListener<ContextRefreshedEvent> applicationListener() {
        return new QuoteApplicationListener();
    }

    @Bean
    public IQuote quote() {
        return new TerminatorQuote();
    }
}
