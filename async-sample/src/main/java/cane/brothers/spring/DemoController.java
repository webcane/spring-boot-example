package cane.brothers.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cane on 29.03.17.
 */
@RestController
@RequestMapping(value = "/demo/task")
public class DemoController {

    private static final Logger log =
            LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private TaskService service;

    @PutMapping("/{taskId}")
    public ResponseEntity<String> performTask(@PathVariable Integer taskId) {
        log.info("Execute performTask() method in "
                + Thread.currentThread().getName() + " thread.");
        service.startTask(taskId);
        return new ResponseEntity<>("Все путем!", HttpStatus.ACCEPTED);
    }
}
