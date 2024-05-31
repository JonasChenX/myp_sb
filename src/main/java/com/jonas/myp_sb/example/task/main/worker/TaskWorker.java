package com.jonas.myp_sb.example.task.main.worker;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;

/**
 * 實際執行作業的苦力程式介面
 */
public interface TaskWorker {
    /**
     * 是否支援特定的參數。
     *
     * @param parameter
     *            the parameter
     * @return <code>true</code> 代表支援，實作的程式就要好好的檢查好，不要到時候讓人家空歡喜一場，最後只收到一個
     *         Exception；否則請回傳 <code>false</code>。 {@link TaskParameter} 參數物件。
     */
    boolean supports(TaskParameter parameter);

    /**
     * 執行作業。
     *
     * @param taskId
     *            Task 識別碼。
     * @param parameter
     *            Task 參數物件。
     * @return 執行結果物件。
     * @throws Exception
     *             如果實際執行作業的過程發生任何錯誤，不用客氣就直接丟出來吧。
     */
    TaskResult perform(long taskId, TaskParameter parameter) throws Exception;

    /**
     * 儲存 TaskParameter 至資料庫或是其他永久儲存媒體。
     *
     * @param taskId
     *            Task 識別碼。
     * @param parameter
     *            Task 參數物件。
     */
    void persistParameter(long taskId, TaskParameter parameter);

    /**
     * 由資料庫或是其他永久儲存媒體取得 TaskParameter 參數物件。
     *
     * @param taskId
     *            Task 識別碼。
     * @return Task 參數物件。
     */
    TaskParameter retrieveParameter(long taskId);

    /**
     * Delete parameter.
     *
     * @param taskId
     *            the task id
     */
    void deleteParameter(long taskId);

    /**
     * 儲存 TaskResult 至資料庫或是其他永久儲存媒體。
     *
     * @param taskId
     *            Task 識別碼。
     * @param result
     *            Task 結果物件。
     */
    void persistResult(long taskId, TaskResult result);

    /**
     * 由資料庫或是其他永久儲存媒體取得 TaskResult 結果物件。
     *
     * @param taskId
     *            Task 識別碼。
     * @return Task 結果物件。
     */
    TaskResult retrieveResult(long taskId);

    /**
     * Delete result.
     *
     * @param taskId
     *            the task id
     */
    default void deleteResult(long taskId) {
        // 預設不做任何事。
    }

    /**
     * 清除作業，不管作業有沒有執行完成都會被呼叫。
     *
     @param taskId Task 識別碼。
     */
    default void cleanUp(long taskId) {
        // 預設不做任可事
    }
}
