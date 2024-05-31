package com.jonas.myp_sb.example.task.main.scheduling.filter;

import com.jonas.myp_sb.example.task.main.scheduling.TaskWorkerJobProxy;
import org.jobrunr.jobs.JobDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class JobDetailsUtils {
    private static final Logger logger = LoggerFactory.getLogger(JobDetailsUtils.class);

    private JobDetailsUtils() {}

    public static boolean isFromTaskDetail(JobDetails jobDetails) {
        return (TaskWorkerJobProxy.class.getName().equals(jobDetails.getClassName()) && "perform".equals(jobDetails.getMethodName()));
    }

    public static long unpackedAndTakeOutTaskId(JobDetails jobDetails) {
        logger.info("unpackedAndTakeOutTaskId");
        Assert.isTrue(jobDetails.getJobParameters().size() == 2, "mismatch for TaskWorkerJobProxy.perform(long)");
        Assert.isTrue(jobDetails.getJobParameters().get(0).getObject() != null, "task id must be not null");
        return Long.parseLong(jobDetails.getJobParameters().get(0).getObject().toString());
    }
}
