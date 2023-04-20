package com.jonas.myp_sb.example.task.main.worker;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;
import org.springframework.stereotype.Component;

import java.time.Instant;

public abstract class AbstractDataLogTaskWorker extends AbstractTaskWorker {
    /*
     * (non-Javadoc)
     *
     * @see gov.fdc.acs.task.worker.TaskWorker#perform(int,
     * gov.fdc.acs.task.TaskParameter)
     */
    public final TaskResult perform(long taskId, TaskParameter parameter) throws Exception {
        TaskResult result = performWorker(taskId, parameter);
        return result;
    }

    public abstract TaskResult performWorker(long taskId, TaskParameter parameter) throws Exception;
}
