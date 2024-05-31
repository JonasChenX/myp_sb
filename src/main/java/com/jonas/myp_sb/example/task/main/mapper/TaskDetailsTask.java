package com.jonas.myp_sb.example.task.main.mapper;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;

import java.time.Instant;

public class TaskDetailsTask implements Task{
    private long taskId;
    private String jobId;
    private AcsTaskDetails.Status status;
    private String message;
    private String description;
    private String modelId;
    private Instant commitTimestamp;
    private Instant startTimestamp;
    private Instant endTimestamp;
    private String type;
    private String committer;
    private String committerOrganCd;

    @Override
    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public AcsTaskDetails.Status getStatus() {
        return status;
    }

    public void setStatus(AcsTaskDetails.Status status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public Instant getCommitTimestamp() {
        return commitTimestamp;
    }

    public void setCommitTimestamp(Instant commitTimestamp) {
        this.commitTimestamp = commitTimestamp;
    }

    @Override
    public Instant getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Instant startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @Override
    public Instant getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Instant endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    @Override
    public String getCommitterOrganCd() {
        return committerOrganCd;
    }

    public void setCommitterOrganCd(String committerOrganCd) {
        this.committerOrganCd = committerOrganCd;
    }
}
