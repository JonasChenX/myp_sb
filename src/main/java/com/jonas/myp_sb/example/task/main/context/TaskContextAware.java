package com.jonas.myp_sb.example.task.main.context;

/**
 * 需要被注入 {@link TaskContext} 的物件。
 */
public interface TaskContextAware {
    /**
     * 設定 TaskContext。
     *
     * @param context
     *            TaskContext 物件。
     */
    void setContext(TaskContext context);

    /**
     * 取得 TaskContext。
     *
     * @return TaskContext 物件。
     */
    TaskContext getContext();

    /**
     * 清除 TaskContext。
     */
    void clearContext();
}
