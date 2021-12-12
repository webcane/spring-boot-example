package cane.brothers.spring.quote;

/**
 * Created by cane
 */
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
