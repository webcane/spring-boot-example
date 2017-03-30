package cane.brothers.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

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

    @Async
    @Override
    public Future<TaskState> startTaskWithResult(Integer taskId) {
        Random rnd = new Random();
        if(rnd.nextBoolean()) {
            log.info("Execute startTask() method in "
                    + Thread.currentThread().getName() + " thread.");
            printTask(taskId);
            return new AsyncResult<>(new TaskState("Отработали успешно!"));
        } else {
            return new AsyncResult<>(new TaskState("Хьюстон, у нас проблемы!"));
        }
    }

    private void printTask(Integer taskId) {
        log.info("= long task started");
        for (int i = 1; i <= taskId; i++) {
            printService.printStage(i);
        }
        log.info("= long task finished");
    }
}
