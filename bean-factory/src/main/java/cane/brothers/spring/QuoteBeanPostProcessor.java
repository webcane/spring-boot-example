package cane.brothers.spring;

import cane.brothers.spring.quote.TerminatorQuote;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by cane
 */
public class QuoteBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof TerminatorQuote) {
            TerminatorQuote quote = (TerminatorQuote) bean;
            quote.setMessage("Hello, World!");
            return quote;
        }
        return bean;
    }
}
