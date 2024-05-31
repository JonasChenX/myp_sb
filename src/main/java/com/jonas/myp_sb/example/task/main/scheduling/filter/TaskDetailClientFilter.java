package com.jonas.myp_sb.example.task.main.scheduling.filter;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.service.AcsTaskDetailsService;
import org.jobrunr.jobs.AbstractJob;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.JobDetails;
import org.jobrunr.jobs.filters.JobClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jonas.myp_sb.example.task.main.scheduling.filter.JobDetailsUtils.isFromTaskDetail;
import static com.jonas.myp_sb.example.task.main.scheduling.filter.JobDetailsUtils.unpackedAndTakeOutTaskId;

public class TaskDetailClientFilter implements JobClientFilter {
    private static final Logger logger = LoggerFactory.getLogger(TaskDetailClientFilter.class);

    private final AcsTaskDetailsService taskDetailsService;

    public TaskDetailClientFilter(AcsTaskDetailsService taskDetailsService) {
        this.taskDetailsService = taskDetailsService;
    }

    @Override
    public void onCreating(AbstractJob abstractJob) {
        logger.info("onCreating");
        JobDetails jobDetails = abstractJob.getJobDetails();
        if (!isFromTaskDetail(jobDetails) && !(abstractJob instanceof Job)) {
            return;
        }
        usingJobToUpdateTaskDetail((Job) abstractJob);
    }

    @Override
    public void onCreated(AbstractJob abstractJob) {
        // no need to implement
    }

    private void usingJobToUpdateTaskDetail(Job jobToSave) {
        long taskId = unpackedAndTakeOutTaskId(jobToSave.getJobDetails());

        logger.info("Associating task {} and job {}", taskId, jobToSave.getId());
        final AcsTaskDetails detail = taskDetailsService.findById(taskId);
        detail.setJobId(jobToSave.getId().toString());

        taskDetailsService.save(detail);
        jobToSave.getMetadata().put("TASK_ID", taskId);
    }
}
