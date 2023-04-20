package com.jonas.myp_sb.example.task.main.scheduling;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.mapper.Task;
import com.jonas.myp_sb.example.task.main.mapper.TaskMapper;
import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.service.AcsTaskDetailsService;
import com.jonas.myp_sb.example.task.main.mapper.TaskDetailsTask;
import com.jonas.myp_sb.example.task.main.worker.TaskWorker;
import com.jonas.myp_sb.example.task.main.worker.TaskWorkerRepository;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.states.StateName;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.JobNotFoundException;
import org.jobrunr.storage.StorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Component
public class AcsTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(AcsTaskScheduler.class);
    public static final String REASON_JOB_DELETED_BY_USER = "Job deleted by user.";

    private final TaskWorkerRepository taskWorkerRepository;

    private final AcsTaskDetailsService taskDetailsService;

    private final JobScheduler jobScheduler;

    private final StorageProvider storageProvider;

    private final TaskWorkerJobProxy taskWorkerJobProxy;

    private final TaskMapper taskMapper;

    private final TaskProperties taskProperties;

    public AcsTaskScheduler(
            TaskWorkerRepository taskWorkerRepository,
            AcsTaskDetailsService taskDetailsService,
            JobScheduler jobScheduler,
            StorageProvider storageProvider,
            TaskWorkerJobProxy taskWorkerJobProxy,
            TaskMapper taskMapper,
            TaskProperties taskProperties) {
        this.taskWorkerRepository = taskWorkerRepository;
        this.taskDetailsService = taskDetailsService;
        this.jobScheduler = jobScheduler;
        this.storageProvider = storageProvider;
        this.taskWorkerJobProxy = taskWorkerJobProxy;
        this.taskMapper = taskMapper;
        this.taskProperties = taskProperties;
    }

    /**
     * 取得 Task。
     *
     * @param taskId 作業編號。
     * @return 對應的作業。
     */
    public Task getTask(long taskId) {
        AcsTaskDetails task = taskDetailsService.findById(taskId);
        return taskMapper.form(task);
    }

    /**
     * 指定選案樣式 ID 建立 Task 物件。
     *
     * @param committer   作業提交者。
     * @param parameter   Task 執行參數。
     * @param description 工作說明。
     * @param modelId     選案樣式 ID，可以為 {@code null}。
     * @return Task 物件。
     */
    @Transactional(propagation = REQUIRED)
    public Task createTask(String committer, TaskParameter parameter, String description, String modelId) {
        TaskDetailsTask task = persistentDetailAndEnqueueJob(committer, parameter, description, modelId);

        persistParameter(task, parameter);
        return task;
    }

    private void persistParameter(Task task, TaskParameter parameter) {
        final TaskWorker worker = taskWorkerRepository.getWorkerInstance(task.getType());
        worker.persistParameter(task.getTaskId(), parameter);
        log.debug("Parameter of task {} has been persistent.", task.getTaskId());
    }

    private TaskDetailsTask persistentDetailAndEnqueueJob(String committer, TaskParameter parameter, String description, String modelId) {
        String workerName = taskWorkerRepository.getSupportedWorker(parameter);

        AcsTaskDetails taskDetail = new AcsTaskDetails();
        taskDetail.setCommitter(committer);
        taskDetail.setModelId(modelId);
        taskDetail.setCommitTimestamp(Instant.now());
        taskDetail.setStatus(AcsTaskDetails.Status.NEW);
        taskDetail.setType(workerName);
        taskDetail.setDescription(description);
        taskDetail.setDeleteMk("N");

        taskDetail = taskDetailsService.save(taskDetail);
        Long taskId = taskDetail.getTaskId();
        if (taskId == null) {
            throw new RuntimeException("取得 taskId 時發生錯誤，請確認資料表 SEQUENCE 設定正確");
        }
        log.info("TaskDetail created: " + taskDetail);

        UUID uuid = enqueue(taskDetail.getTaskId());
        log.info("Task {} enqueued as job {}.", taskDetail.getTaskId(), uuid);

        final TaskDetailsTask task = taskMapper.form(taskDetail);
        task.setJobId(uuid.toString());
        return task;
    }

    private UUID enqueue(long taskId) {
        return this.jobScheduler.enqueue(() -> this.taskWorkerJobProxy.perform(taskId, JobContext.Null)).asUUID();
    }

    public void markAsDeleted(long taskId) {
        // TODO 由 JobScheduler 的 meta 中找，看會不會比較快
        final AcsTaskDetails detail = taskDetailsService.findById(taskId);
        final String jobId = detail.getJobId();
        final UUID jobUuid = UUID.fromString(jobId);
        if (taskProperties.isApplyJobRunr516Workaroud()) {
            markTaskDetailAsDeleted(detail);

        } else {
            try {
                jobScheduler.delete(jobUuid, REASON_JOB_DELETED_BY_USER);
                log.info(
                        "Task {} (backed by job {}) has been deleted.",
                        taskId, jobId);

            } catch (JobNotFoundException e) {
                // TODO 作業可能已經被 JobRunr 給刪除了，就記下來不要再透過 JobRunr 處理囉
                log.warn(
                        "Tak {} (backed by job {}) was not found inside JobRunr, it may have been removed by JobRunr maintenance work.",
                        taskId, jobId);
                markTaskDetailAsDeleted(detail);
            }
        }
    }

    private void markTaskDetailAsDeleted(AcsTaskDetails detail) {
//        if (AcsTaskDetails.Status.END_STATUSES.contains(detail.getStatus()) ) {
//            throw new TaskException(MessageCodes.STS_TASK_0011_E(detail.getTaskId(), detail.getStatus().getDescription()));

//        } else {
        detail.setStatus(AcsTaskDetails.Status.CANCELLED);
        detail.setEndTimestamp(Instant.now());
        detail.setDeleteMk("Y");
        taskDetailsService.save(detail);

        log.info(
                "Task {} (backed by job {}) has been marked as deleted.",
                detail.getTaskId(), detail.getJobId());
//        }
    }

    public void completeStagedTask(long taskId) {
        final AcsTaskDetails detail = taskDetailsService.findById(taskId);
        assertStagedTaskInValidStatus(detail);

        detail.setStatus(AcsTaskDetails.Status.TERMINATED);
        detail.setEndTimestamp(Instant.now());
        taskDetailsService.save(detail);

        log.info("Task {} (backed by job {}) has been set to TERMINATED.", taskId, detail.getJobId());
    }

    public void failStagedTask(long taskId, String message) {
        final AcsTaskDetails detail = taskDetailsService.findById(taskId);
        assertStagedTaskInValidStatus(detail);

        detail.setStatus(AcsTaskDetails.Status.FAILED);
        detail.setEndTimestamp(Instant.now());
        detail.setMessage(message);
        taskDetailsService.save(detail);

        log.info("Task {} (backed by job {}) has been set to FAILED.", taskId, detail.getJobId());
    }

    private void assertStagedTaskInValidStatus(AcsTaskDetails taskDetails) {
        if (!taskWorkerRepository.isStaged(taskDetails.getType())) {
            throw new RuntimeException("作業 " + taskDetails.getTaskId() + " (類型: " + taskDetails.getType() + ") 並不是分階段作業，無法變更狀態為成功或失敗");
        }

        final Job job = storageProvider.getJobById(UUID.fromString(taskDetails.getJobId()));
        if ((job.getJobState().getName() != StateName.SUCCEEDED) || (taskDetails.getStatus() != AcsTaskDetails.Status.RUNNING)) {
            throw new RuntimeException("分段作業 "+ taskDetails.getTaskId() +" 的狀態為"+ taskDetails.getType() +"，無法變更狀態為成功或失敗。");
        }
    }


}
