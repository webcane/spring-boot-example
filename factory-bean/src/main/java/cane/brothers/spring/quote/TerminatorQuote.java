package cane.brothers.spring.quote;

import org.springframework.stereotype.Component;

/**
 * Created by cane
 */
@Component
public class TerminatorQuote implements Quote {

    private String message;

    @Override
    public void sayQuote() {
        System.out.println("message=" + message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
