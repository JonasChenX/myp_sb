package com.jonas.myp_sb.example.task.main.worker;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
public abstract class AbstractDataLogTaskWorker extends AbstractTaskWorker {
    /*
     * (non-Javadoc)
     *
     * @see gov.fdc.acs.task.worker.TaskWorker#perform(int,
     * gov.fdc.acs.task.TaskParameter)
     */
    public final TaskResult perform(long taskId, TaskParameter parameter) throws Exception {
        log.info("執行performWorker");
        TaskResult result = performWorker(taskId, parameter);
        log.info("performWorker結束");
        return result;
    }

    public abstract TaskResult performWorker(long taskId, TaskParameter parameter) throws Exception;
}
