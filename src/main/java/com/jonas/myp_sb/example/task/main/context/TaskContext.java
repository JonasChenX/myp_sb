package com.jonas.myp_sb.example.task.main.context;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;

import java.util.HashMap;
import java.util.Map;

public class TaskContext {
    private final AcsTaskDetails taskDetail;

    /** The parameter. */
    private TaskParameter parameter;

    /** The result. */
    private TaskResult result;

    /** The properties. */
    private final Map<String, Object> properties;

    /**
     * Instantiates a new task context.
     *
     * @param taskDetail
     *            the task detail
     */
    public TaskContext(AcsTaskDetails taskDetail) {
        this(taskDetail, new HashMap<>());
    }

    /**
     * Instantiates a new task context.
     *
     * @param taskDetail
     *            the task detail
     * @param properties
     *            the properties
     */
    public TaskContext(AcsTaskDetails taskDetail, Map<String, ?> properties) {
        this.taskDetail = taskDetail;
        this.properties = new HashMap<>(properties);
    }

    /**
     * 取得 Task 識別碼。
     *
     * @return Task 識別碼。
     */
    public long getTaskId() {
        return getTaskDetail().getTaskId();
    }

    /**
     * 取得訊息。
     *
     * @return 訊息。
     */
    public String getMessage() {
        return getTaskDetail().getMessage();
    }

    /**
     * 取得 TaskParameter 參數。
     *
     * @param <T>
     *            TaskParameter 參數型別。
     * @return TaskParameter 參數物件。
     */
    @SuppressWarnings("unchecked")
    public <T extends TaskParameter> T getParameter() {
        return (T) parameter;
    }

    /**
     * 取得 TaskResult 結果。
     *
     * @param <T>
     *            TaskResult 結果型別。
     * @return TaskResult 結果物件。
     */
    @SuppressWarnings("unchecked")
    public <T extends TaskResult> T getResult() {
        return (T) result;
    }

    /**
     * 設定訊息。
     *
     * @param message
     *            訊息。
     */
    public void setMessage(String message) {
        getTaskDetail().setMessage(message);
    }

    /**
     * 設定 TaskParameter 參數。
     *
     * @param parameter
     *            TaskParameter 參數物件。
     */
    public void setParameter(TaskParameter parameter) {
        this.parameter = parameter;
    }

    /**
     * 設定 TaskResult 結果。
     *
     * @param result
     *            TaskResult 結果物件。
     */
    public void setResult(TaskResult result) {
        this.result = result;
    }

    /**
     * 取得屬性值。
     *
     * @param <T>
     *            屬性值型態。
     * @param name
     *            屬性名稱。
     * @return 屬性值，或是 {@code null} 代表沒有對應的屬性。
     */
    @SuppressWarnings("unchecked")
    public <T> T getProperty(String name) {
        return (T) properties.get(name);
    }

    /**
     * 設定屬性值。
     *
     * @param <T>
     *            屬性值型別。
     * @param name
     *            屬性名稱。
     * @param value
     *            屬性值。
     * @return 該屬性名稱所關聯的前一值，或是 {@code null}。
     */
    @SuppressWarnings("unchecked")
    public <T> T setProperty(String name, T value) {
        return (T) properties.put(name, value);
    }

    /**
     * 取得狀態。
     *
     * @return status 狀態。
     */
    public AcsTaskDetails.Status getStatus() {
        return getTaskDetail().getStatus();
    }

    private AcsTaskDetails getTaskDetail() {
        return taskDetail;
    }
}
