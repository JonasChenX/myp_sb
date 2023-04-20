package com.jonas.myp_sb.example.task.test;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;
import com.jonas.myp_sb.example.task.main.worker.AbstractDataLogTaskWorker;
import org.springframework.stereotype.Component;

@Component("reportModuleModelWorker")
public class ReportModuleModelWorker extends AbstractDataLogTaskWorker {

    @Override
    public boolean supports(TaskParameter parameter) {
        return false;
    }

    // to do -> 1
    @Override
    public void persistParameter(long taskId, TaskParameter parameter) {

    }

    // 2
    @Override
    public TaskParameter retrieveParameter(long taskId) {
        return null;
    }

    // 3
    @Override
    public TaskResult performWorker(long taskId, TaskParameter parameter) throws Exception {
        return null;
    }

    // 4
    @Override
    public void persistResult(long taskId, TaskResult result) {

    }

    @Override
    public TaskResult retrieveResult(long taskId) {
        return null;
    }

    @Override
    public void deleteParameter(long taskId) {

    }
}
