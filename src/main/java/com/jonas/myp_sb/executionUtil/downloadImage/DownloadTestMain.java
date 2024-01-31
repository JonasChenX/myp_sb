package com.jonas.myp_sb.executionUtil.downloadImage;

public class DownloadTestMain {

    //創建資料夾名稱
    static String FOLDER_NM = "新增資料夾01";
    //下載網址
    static String URL_STR = "https://releases.ubuntu.com/22.04.3/ubuntu-22.04.3-live-server-amd64.iso";

    /**
     * 下載測試
     */
    public static void main(String[] args) {
        DownloadService downloadService = new DownloadService();
        downloadService.downloadByTest(URL_STR, FOLDER_NM);
    }
}
