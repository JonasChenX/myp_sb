package com.jonas.myp_sb.executionUtil.downloadImage;

public class DownloadImageMain {

    //起始頁
    static int SNO = 1;
    //最後一頁
    static int ENO = 208;
    //讀取網頁圖片「流水號前面的網址」
    static String URL_PRE = "";
    //讀取網頁圖片「格式」 如：.jpg
    static String UEL_SUF = ".jpg";
    //創建資料夾名稱
    static String FOLDER_NM = "新增資料夾01";

    /**
     * 下載漫畫(連號用)
     */
    public static void main(String[] args) {
        DownloadService downloadService = new DownloadService();
        downloadService.downloadByImage(SNO, ENO, URL_PRE, UEL_SUF, FOLDER_NM);
    }

}
