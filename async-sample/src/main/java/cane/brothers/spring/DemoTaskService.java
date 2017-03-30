package cane.brothers.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by cane on 29.03.17.
 */
@Service
public class DemoTaskService implements TaskService {

    private static final Logger log =
            LoggerFactory.getLogger(DemoTaskService.class);

    @Autowired
    private PrintService printService;


    @Async
    @Override
    public void startTask(Integer taskId) {
        log.info("Execute startTask() method in "
                + Thread.currentThread().getName() + " thread.");
        printTask(taskId);
    }

    private void printTask(Integer taskId) {
        log.info("= long task started");
        for (int i = 1; i <= taskId; i++) {
            printService.printStage(i);
        }
        log.info("= long task finished");
    }
}
