package com.jonas.myp_sb.executionUtil.downloadImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class downloadImageService {

    /**
     * 下載漫畫功能
     * (需要是連號)
     */

    //讀取網頁圖片「流水號前面的網址」
    static String  URL_PRE = "";
    //讀取網頁圖片「格式」 如：.jpg
    static String UEL_SUF = ".jpg";
    //起始頁
    static int SNO = 1;
    //最後一頁
    static int ENO = 208;
    //創建資料夾名稱
    static String FILE_NAME = "";
    //創建資料夾路徑
    static String PATH_NAME = "";


    public static void main(String[] args) {
        downloadImage();
    }

    private static void downloadImage() {
        File folder = new File(PATH_NAME + FILE_NAME);
        for(int i= SNO; i <= ENO; i++) {
            String iStr = String.format("%03d", i); //若未滿兩位數，前面補0
//            String iStr = String.valueOf(i);
            String surl = URL_PRE + iStr + UEL_SUF;
            System.out.println(surl);

            /* 毫秒設定 */
            int max =1;
            int min =1;

            try {
                Thread.sleep((long) (Math.floor(Math.random()*(max - min))+min)); //不讓http認為是程式操作(速度太快)，因此模擬人為的速度
                URL url = new URL(surl);

                String lastStr = "_";

                //定義檔案名稱
                String filename = surl.substring(surl.lastIndexOf(lastStr)+1);

                if(!folder.exists()) {
                    folder.mkdir();
                }

                File file = new File(folder,filename);
                try(
                        InputStream is = url.openStream();
                        FileOutputStream fos = new FileOutputStream(file);
                ){
                    byte[] b =new byte[81920];
                    int len = 0;
                    while ((len = is.read(b)) !=-1) {
                        fos.write(b,0,len);
                    }
                    System.out.println("寫出"+filename+"成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
