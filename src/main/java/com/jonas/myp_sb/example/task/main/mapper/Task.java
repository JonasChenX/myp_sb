package com.jonas.myp_sb.example.task.main.mapper;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;

import java.time.Instant;

/**
 * Task 作業物件。並可以提供作業進行流程的相關資訊資訊，包括
 * <p>
 * {@link AcsTaskDetails.Status} 狀態及處理訊息等。
 * <p>
 * 可能的狀態及轉換如下所示:
 * <ul>
 * <li>{@link AcsTaskDetails.Status#NEW} -&gt;</li>
 * <li>{@link AcsTaskDetails.Status#STARTING} -&gt;</li>
 * <li>{@link AcsTaskDetails.Status#RUNNING} -&gt;</li>
 * <li>{@link AcsTaskDetails.Status#STOPPING} -&gt;</li>
 * <li>{@link AcsTaskDetails.Status#TERMINATED}</li>
 * </ul>
 */
public interface Task {
    /**
     * 取得 Task 識別碼。
     *
     * @return 識別碼。
     */
    long getTaskId();

    /**
     * 取得 Task 狀態。
     *
     * @return {@link AcsTaskDetails.Status} 狀態物件。
     */
    AcsTaskDetails.Status getStatus();

    /**
     * 取得 Task 處理訊息，可以用來更詳細的描述 {@link AcsTaskDetails.Status}。
     * <p>
     * 例如: "<u>10%</u>", "<u>50%</u>"，或是 "<u>正在取得同群定義...</u>",
     * "<u>正在執行異常評分運算...</u>" 等文字訊息。
     *
     * @return 處理訊息。
     */
    String getMessage();

    /**
     * 取得工作說明訊息。
     *
     * @return 說明訊息。
     */
    String getDescription();

    /**
     * 取得選案樣式ID。
     *
     * @return 選案樣式ID。
     */
    String getModelId();

    /**
     * 取得提交時間。
     *
     * @return 提交時間。
     */
    Instant getCommitTimestamp();

    /**
     * 取得實際開始執行時間。
     *
     * @return 實際開始執行時間。
     */
    Instant getStartTimestamp();

    /**
     * 取得實際結束時間。
     *
     * @return 實際結束時間。
     */
    Instant getEndTimestamp();

    /**
     * 取得 Task 類型。
     *
     * @return Task 類型。
     */
    String getType();

    /**
     * 取得提交人代碼。
     *
     * @return 提交人代碼。
     */
    String getCommitter();

    /**
     * 取得提交人區局代碼。
     *
     * @return String 提交人區局代碼。
     */
    String getCommitterOrganCd();
}
