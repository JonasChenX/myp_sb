package com.jonas.myp_sb.example.task.main.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import static java.lang.String.format;

@ToString
public class AcsTaskDetails implements Serializable {
    public static final int TYPE_LENGTH_LIMIT = 32;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acsTaskDetailGenerator")
    @SequenceGenerator(name = "acsTaskDetailGenerator", sequenceName = "sts_acs_task_detail_pk_seq", initialValue = 1, allocationSize = 1)
    private Long taskId;

    @Size(max = 36)
    @Column(name = "job_id", length = 36)
    private String jobId;

    @NotNull
    @Column(name = "status", nullable = false)
    @JsonIgnore
    private Integer status;

    @NotNull
    @Size(max = 32)
    @Column(name = "type", length = 32, nullable = false)
    private String type;

    @Size(max = 256)
    @Column(name = "message", length = 256)
    private String message;

    @NotNull
    @Size(max = 8)
    @Column(name = "committer", length = 8, nullable = false)
    private String committer;

    @Column(name = "commit_timestamp")
    private Instant commitTimestamp = Instant.now();

    @Column(name = "start_timestamp")
    private Instant startTimestamp;

    @Column(name = "end_timestamp")
    private Instant endTimestamp;

    @NotNull
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    @LastModifiedDate
    @Column(name = "last_modified_timestamp")
    private Instant lastModifiedTimestamp;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Size(max = 2)
    @Column(name = "delete_mk", length = 2)
    private String deleteMk;

    @Size(max = 14)
    @Column(name = "model_id", length = 14)
    private String modelId;

    //taskId
    public Long getTaskId() {
        return taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public AcsTaskDetails taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    //jobId
    public String getJobId() {
        return jobId;
    }
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public AcsTaskDetails jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    //status
    @JsonProperty
    public Status getStatus() {
        if (this.status != null) {
            return Status.valueOf(this.status.intValue());
        } else {
            return null;
        }
    }
    @JsonProperty
    public void setStatus(Status status) {
        if (status != null) {
            this.status = status.getCode();
        } else {
            this.status = null;
        }
    }
    public AcsTaskDetails status(Status status) {
        setStatus(status);
        return this;
    }

    //type
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public AcsTaskDetails type(String type) {
        this.type = type;
        return this;
    }

    //message
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public AcsTaskDetails message(String message) {
        setMessage(message);
        return this;
    }

    //committer
    public String getCommitter() {
        return committer;
    }
    public void setCommitter(String committer) {
        this.committer = committer;
    }
    public AcsTaskDetails committer(String committer) {
        this.committer = committer;
        return this;
    }

    //commitTimestamp
    public Instant getCommitTimestamp() {
        return commitTimestamp;
    }
    public void setCommitTimestamp(Instant commitTimestamp) {
        this.commitTimestamp = commitTimestamp;
    }

    //startTimestamp
    public Instant getStartTimestamp() {
        return startTimestamp;
    }
    public void setStartTimestamp(Instant startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    //endTimestamp
    public Instant getEndTimestamp() {
        return endTimestamp;
    }
    public void setEndTimestamp(Instant endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    //retryCount
    public Integer getRetryCount() {
        return retryCount;
    }
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    //lastModifiedTimestamp
    public Instant getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }
    public void setLastModifiedTimestamp(Instant lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    //description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //deleteMk
    public String getDeleteMk() {
        return deleteMk;
    }
    public void setDeleteMk(String deleteMk) {
        this.deleteMk = deleteMk;
    }

    //modelId
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcsTaskDetails)) {
            return false;
        }
        return taskId != null && taskId.equals(((AcsTaskDetails) o).taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum Status {
        /**
         * 代表 Task 被建立了，但是還沒有被執行到。
         */
        NEW(0, "新建立"),

        /**
         * 代表 Task 正要轉換到 {@link #RUNNING}。
         */
        STARTING(1, "準備開始執行"),

        /**
         * 代表 Task 正在執行中。
         */
        RUNNING(2, "執行中"),

        /**
         * 代表 Task 正在轉換到 {@link #TERMINATED}。
         */
        STOPPING(3, "準備結束執行"),

        /**
         * 代表 Task 已經正常執行完畢。
         */
        TERMINATED(4, "執行成功"),

        /**
         * 代表 Task 在執行過程中發生錯誤。
         */
        FAILED(5, "執行失敗"),

        /**
         * 代表 Task 已經被取消。
         */
        CANCELLED(6, "已被取消");

        /**
         * 結束狀態集合，包含 {@link #TERMINATED}, {@link #FAILED} 及 {@link #CANCELLED}。
         */
        public static final Set<Status> END_STATUSES = ImmutableSet.of(TERMINATED, FAILED, CANCELLED);

        /**
         * The code.
         */
        private final int code;

        /**
         * The description.
         */
        private final String description;

        /**
         * Instantiates a new status.
         *
         * @param code        the code
         * @param description the description
         */
        Status(int code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }

        /**
         * 取得對應的代碼數值，如果資料庫不支援 Enumeration，可以改用此數值代替。
         *
         * @return 代碼數值。
         */
        @JsonValue
        public int getCode() {
            return code;
        }

        /**
         * 取得 description.
         *
         * @return description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Value of.
         *
         * @param code the code
         * @return status
         */
        @JsonCreator
        public static Status valueOf(int code) {
            for (Status status : values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            String message = format("Unknown Status code %d.", code);
            throw new IllegalArgumentException(message);
        }
    }
}
