package com.jonas.myp_sb.example.task.main.scheduling;

import com.jonas.myp_sb.example.task.main.context.TaskContext;
import com.jonas.myp_sb.example.task.main.context.TaskContextAware;
import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;
import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.service.AcsTaskDetailsService;
import com.jonas.myp_sb.example.task.main.worker.TaskWorker;
import com.jonas.myp_sb.example.task.main.worker.TaskWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskWorkerJobProxy {

    private final AcsTaskDetailsService acsTaskDetailsService;
    private final TaskWorkerRepository taskWorkerRepository;

    public TaskWorkerJobProxy(AcsTaskDetailsService acsTaskDetailsService, TaskWorkerRepository taskWorkerRepository) {
        this.acsTaskDetailsService = acsTaskDetailsService;
        this.taskWorkerRepository = taskWorkerRepository;
    }

//    @JobProxy(
//            jobMetadataLookupStrategy = TaskWorkerJobMetadataLookupStrategy.class,
//            jobFilters = { TaskDetailClientFilter.class, TaskDetailApplyStateFilter.class }
//    )
    public void perform(long taskId, JobContext jobContext) throws Exception {
        System.err.println("77777777777");
        AcsTaskDetails detail = this.acsTaskDetailsService.findById(taskId);

        String workerName = detail.getType();
        TaskWorker taskWorker = this.taskWorkerRepository.getWorkerInstance(workerName);

        TaskContext context = new TaskContext(detail);
        if (taskWorker instanceof TaskContextAware) {
            ((TaskContextAware) taskWorker).setContext(context);
        }
        try {
            // 取得參數
            assertNotInterrupted("作業在取得參數前已被中斷");
            log.info("取得參數");
            TaskParameter parameter = taskWorker.retrieveParameter(taskId);
            context.setParameter(parameter);

            // 執行作業
            assertNotInterrupted("作業在執行前已被中斷");
            log.info("執行作業");
            TaskResult result = taskWorker.perform(taskId, parameter);
            context.setResult(result);

            // 儲存結果
            log.info("儲存結果");
            assertNotInterrupted("作業在儲存結果前已先被中斷");
            taskWorker.persistResult(taskId, result);
        } catch (InterruptedException e) {
            log.info("Task {} (backed by job {}) interrupted.", taskId, jobContext.getJobId(), e);
            String message = e.getMessage();
            if (message != null) {
                message = "作業已被中斷: " + message;
            } else {
                message = "作業已被中斷。";
            }
            detail.setMessage(message);
            throw e;
        } catch (Exception e) {
            log.warn("Task {} (backed by job {}) run failed.", taskId, jobContext.getJobId(), e);
            detail.setMessage("作業執行失敗，錯誤原因: " + e.getMessage());
            throw e;
        } finally {
            try {
                taskWorker.cleanUp(taskId);
            } finally {
                try {
                    log.info("儲存detail:{}",detail);
                    acsTaskDetailsService.save(detail);
                } finally {
                    if (taskWorker instanceof TaskContextAware) {
                        ((TaskContextAware) taskWorker).clearContext();
                    }
                }
            }
        }
    }

    private void assertNotInterrupted(String message) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException(message);
        }
    }
}
