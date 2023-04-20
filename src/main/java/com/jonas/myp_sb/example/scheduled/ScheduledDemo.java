package com.jonas.myp_sb.example.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling //開啟任務計劃功能
public class ScheduledDemo {

    /**
     * 設置任務計劃
     * @Scheduled 帶入參數
     *
     * fixedRate：固定速率。表示任務開始執行後固定的時間間隔執行一次，不考慮任務的實際執行時間。例如：@Scheduled(fixedRate = 5000) 表示每 5 秒執行一次任務。
     * fixedDelay：固定延遲。表示任務執行完畢後固定的時間間隔再次執行，考慮任務的實際執行時間。例如：@Scheduled(fixedDelay = 5000) 表示任務執行完畢後等待 5 秒後再次執行任務。
     * initialDelay：初始延遲。表示在 Spring 啟動後，任務開始執行之前的初始延遲時間。例如：@Scheduled(initialDelay = 10000, fixedDelay = 5000) 表示任務將在 Spring 啟動後等待 10 秒後首次執行，之後每 5 秒執行一次。
     * cron：Cron 表達式。使用 Cron 表達式設置任務的運行時間。例如：@Scheduled(cron = "0 0 12 * * ?") 表示在每天的中午 12 點執行任務。
     * */

//    @Scheduled(initialDelay = 1000, fixedRate = 5000) //Spring啟動後1秒過後執行
    public void doTask1() {
        System.out.println("每五秒執行一次");
    }

    @Scheduled(cron = "0 0 0 1 */1 ?") // 每個月的1日觸發
    public void doTask2() {
        System.out.println("每個月的1日執行一次");
    }

}
