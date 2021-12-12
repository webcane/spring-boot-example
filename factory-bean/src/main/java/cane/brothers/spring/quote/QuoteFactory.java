package cane.brothers.spring.quote;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by cane
 * @see AbstractFactoryBean
 */
public class QuoteFactory implements FactoryBean<Quote> {

    private String message;

    public QuoteFactory(String message) {
        setMessage(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Quote getObject() throws Exception {
        TerminatorQuote quote = new TerminatorQuote();
        quote.setMessage(message);
        return quote;
    }

    @Override
    public Class<?> getObjectType() {
        return TerminatorQuote.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
