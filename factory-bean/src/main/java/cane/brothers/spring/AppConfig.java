package cane.brothers.spring;

import cane.brothers.spring.quote.Quote;
import cane.brothers.spring.quote.QuoteFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cane
 */
@Configuration
public class AppConfig {

    @Bean(name="quote")
    public QuoteFactory quoteFactory() {
        return new QuoteFactory("Hello, World!");
    }

    @Bean
    public Quote quote() throws Exception {
        return quoteFactory().getObject();
    }


}
