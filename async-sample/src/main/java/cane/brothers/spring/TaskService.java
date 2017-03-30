package cane.brothers.spring;

import java.util.concurrent.Future;

/**
 * Created by cane on 29.03.17.
 */
public interface TaskService {

    void startTask(Integer taskId);

    Future<TaskState> startTaskWithResult(Integer taskId);
}
