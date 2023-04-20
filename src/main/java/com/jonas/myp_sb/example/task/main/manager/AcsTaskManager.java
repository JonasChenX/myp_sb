package com.jonas.myp_sb.example.task.main.manager;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;
import com.jonas.myp_sb.example.task.main.mapper.Task;
import com.jonas.myp_sb.example.task.main.scheduling.AcsTaskScheduler;
import com.jonas.myp_sb.example.task.main.worker.TaskWorker;
import com.jonas.myp_sb.example.task.main.worker.TaskWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Component
@Slf4j
public class AcsTaskManager {
    private final AcsTaskScheduler acsTaskScheduler;
    private final TaskWorkerRepository taskWorkerRepository;

    public AcsTaskManager(AcsTaskScheduler acsTaskScheduler, TaskWorkerRepository taskWorkerRepository) {
        this.acsTaskScheduler = acsTaskScheduler;
        this.taskWorkerRepository = taskWorkerRepository;
    }

    /**
     * 由 id 識別碼取得 Task 物件。
     *
     * @param id 識別碼。
     * @return Task 物件。
     */
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public Task getTask(long id) {
        return acsTaskScheduler.getTask(id);
    }

    /**
     * 建立 Task 物件。
     *
     * @param parameter   Task 執行參數。
     * @param description 工作說明。
     * @return Task 物件。
     */
    @Transactional(propagation = REQUIRES_NEW)
    public Task createTask(TaskParameter parameter, String description) {
        return createTask(parameter, description, null);
    }

    /**
     * 指定選案樣式 ID 建立 Task 物件。
     *
     * @param parameter   Task 執行參數。
     * @param description 工作說明。
     * @param modelId     選案樣式 ID。
     * @return Task 物件。
     */
    @Transactional(propagation = REQUIRES_NEW)
    public Task createTask(TaskParameter parameter, String description, String modelId) {
        String committer = getCommitter();
        Task task = acsTaskScheduler.createTask(committer, parameter, description, modelId);
        log.info("Task created: {}", task.getTaskId());
        return task;
    }

    /**
     * 將 Task 的刪除註記標註為 {@code "Y"}。
     *
     * @param id 作業識別碼。
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void markAsDeleted(long id) {
        this.acsTaskScheduler.markAsDeleted(id);
    }

    /**
     * 通知分階段作業執行完成。
     *
     * @param id 作業識別碼。
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void completeStagedTask(long id) {
        this.acsTaskScheduler.completeStagedTask(id);
    }

    /**
     * 通知分階段作業執行失敗。
     *
     * @param id      作業識別碼。
     * @param message 說明訊息。
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void failStagedTask(long id, String message) {
        this.acsTaskScheduler.failStagedTask(id, message);
    }

    /**
     * 取得指定作業的執行參數。
     *
     * @param id 作業識別碼。
     * @return 作業參數。
     */
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public TaskParameter getParameter(long id) {
        final Task task = getTask(id);
        final TaskWorker worker = taskWorkerRepository.getWorkerInstance(task.getType());
        return worker.retrieveParameter(id);
    }

    /**
     * 取得指定作業的執行結果。
     *
     * @param id 作業識別碼。
     * @return 作業執行結果。
     */
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public TaskResult getResult(long id) {
        final Task task = getTask(id);
        final TaskWorker worker = taskWorkerRepository.getWorkerInstance(task.getType());
        return worker.retrieveResult(id);
    }

    //TODO 待抓userId
    private String getCommitter() {
        return "UserHolder.getUser().getId()";
    }

}
