package cane.brothers.spring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mniedre on 30.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskState {

    private String state;

    public TaskState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskState {" +
                "state='" + state + '\'' +
                '}';
    }
}
