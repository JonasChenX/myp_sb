package com.jonas.myp_sb.example.task.main.scheduling;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.service.AcsTaskDetailsService;
import com.jonas.myp_sb.example.task.main.worker.TaskWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.filters.ApplyStateFilter;
import org.jobrunr.jobs.states.JobState;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.jobrunr.jobs.states.StateName.FAILED_STATES;

@Component
@Slf4j
public class TaskDetailApplyStateFilter implements ApplyStateFilter {

    private final AcsTaskDetailsService taskDetailsService;
    private final TaskWorkerRepository taskWorkerRepository;

    public TaskDetailApplyStateFilter(AcsTaskDetailsService taskDetailsService, TaskWorkerRepository taskWorkerRepository) {
        this.taskDetailsService = taskDetailsService;
        this.taskWorkerRepository = taskWorkerRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void onStateApplied(Job job, JobState oldState, JobState newState) {
        log.info("onStateApplied");

        final AcsTaskDetails detail = taskDetailsService.findByJobId(job.getId().toString());

        updateStatus(job, oldState, newState, detail);
        updateRetryCount(job, detail);

        taskDetailsService.save(detail);

    }

    private void updateStatus(Job job, JobState oldState, JobState newState, AcsTaskDetails detail) {
        log.info("updateStatus oldState:{}",oldState.getName());
        log.info("updateStatus newState:{}",newState.getName());
        if (oldState != null && (oldState.getName() == newState.getName())) {
            return;
        }

        final AcsTaskDetails.Status oldStatus = detail.getStatus();
        switch (newState.getName()) {
            case SCHEDULED:
            case ENQUEUED:
                detail.setStatus(AcsTaskDetails.Status.NEW);
                break;
            case PROCESSING:
                detail.setStatus(AcsTaskDetails.Status.RUNNING);
                detail.setStartTimestamp(Instant.now());
                break;
            case FAILED:
                detail.setStatus(AcsTaskDetails.Status.FAILED);
                detail.setEndTimestamp(Instant.now());
                break;
            case SUCCEEDED:
                if (taskWorkerRepository.isStaged(detail.getType())) {
                    detail.setStatus(AcsTaskDetails.Status.RUNNING);
                    detail.setMessage("分階段作業第一階段作業完成，等候回報成功或失敗");
                } else {
                    detail.setStatus(AcsTaskDetails.Status.TERMINATED);
                    detail.setEndTimestamp(Instant.now());
                }
                break;
            case DELETED:
                detail.setStatus(AcsTaskDetails.Status.CANCELLED);
                detail.setEndTimestamp(Instant.now());
                detail.setDeleteMk("Y");
                break;
        }
        log.info(
                "Job {} (task {}) state changed from {} ({}) to {} ({})",
                job.getId(),
                detail.getTaskId(),
                oldState,
                oldStatus,
                newState,
                detail.getStatus()
        );
    }

    private void updateRetryCount(Job job, AcsTaskDetails detail) {
        final long failureCount = job.getJobStates().stream().filter(FAILED_STATES).count();
        detail.setRetryCount((int) failureCount);
    }
}
