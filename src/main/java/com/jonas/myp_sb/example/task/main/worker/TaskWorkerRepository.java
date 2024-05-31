package com.jonas.myp_sb.example.task.main.worker;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;

/**
 * 管理 {@link TaskWorker} 物件的儲存庫
 */
public interface TaskWorkerRepository {
    /**
     * 取得支援指定 {@link TaskParameter} 的 Worker 名稱。
     *
     * @param parameter TaskParameter 物件。
     * @return 能處理該參數的 Worker 名稱。
     */
    String getSupportedWorker(TaskParameter parameter);

    /**
     * 取得 {@link TaskWorker} 物件。
     *
     * @param workerName Worker 名稱。
     * @return 對應的 Worker 物件。
     */
    TaskWorker getWorkerInstance(String workerName);

    /**
     * 判斷指定 TaskWorker 是否為分階段作業。 (是否標註為 {@link Staged})
     *
     * @param workerName Worker 名稱。
     * @return {@code true} 表示為 {@link Staged} 分階段作業，否則回傳 {@code false}。
     */
    boolean isStaged(String workerName);
}
