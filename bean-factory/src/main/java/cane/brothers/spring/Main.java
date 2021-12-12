package cane.brothers.spring;

import cane.brothers.spring.quote.Quote;
import cane.brothers.spring.quote.TerminatorQuote;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by cane
 */
public class Main {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotatedBeanDefinitionReader beanReader = new AnnotatedBeanDefinitionReader(context);

        System.out.println(context.getBeanDefinitionCount());
        beanReader.register(TerminatorQuote.class);
        beanReader.register(QuoteBeanPostProcessor.class);
        System.out.println(context.getBeanDefinitionCount());
        context.refresh();

        Quote quoteBean = context.getBean(Quote.class);
        quoteBean.sayQuote();

    }
}
