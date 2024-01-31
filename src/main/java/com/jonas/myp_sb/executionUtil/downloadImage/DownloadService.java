package com.jonas.myp_sb.executionUtil.downloadImage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

public class DownloadService {

    //創建資料夾路徑
    static String PATH_NAME = "C:\\Users\\Jonas\\Desktop\\";
    //是否設定為 人為操作
    static Boolean IS_SLEEP = false;

    public static void main(String[] args) throws JsonProcessingException {
        /**
         * 打外部API取得陣列 每一個元素為檔案連結做下載
         */
        //打API 取得資料  JSP或是JSON格式  整理成 陣列[{fileUrl:"",fileNm:""},...]
        //依照 陣列 跑迴圈下載

        String url = "https://api.azurlane-tcg.com/api/pc/getKapaiList";

        Map<String, Object> requestData  = new HashMap<>();
        requestData.put("limit", 1000);
        requestData.put("page", 1);
        requestData.put("code", "%BP02");

        String responseStr = postDataToExternalAPI(url, requestData);

        //若回傳式JSON格式 轉物件
        ObjectMapper objectMapper = new ObjectMapper();
        Map responseMap = objectMapper.readValue(responseStr, Map.class);
        Map<String,Object> dataMap = (Map<String, Object>) responseMap.get("data");
        List<Map> dataList = (List<Map>) dataMap.get("list");

        //reset陣列
        List resetDataList = Collections.singletonList(dataList.stream().map(item -> {
            DownloadDTO downloadDTO = new DownloadDTO();
            downloadDTO.setFileNm(String.valueOf(item.get("code")));
            downloadDTO.setFileUrl(String.valueOf(item.get("img")));
            return downloadDTO;
        }).collect(Collectors.toList()));

        System.out.println(resetDataList);
    }

    public static void downloadByTest(String urlStr,String folderNm){
        String lastStr = "/";
        //定義檔案名稱
        String fileNm = urlStr.substring(urlStr.lastIndexOf(lastStr)+1);
        //下載
        download(fileNm, urlStr, folderNm);
    }

    public static void downloadByImage(int sno, int eno, String urlPre, String urlSuf, String folderNm) {
        for(int i= sno; i <= eno; i++) {
            String iStr = String.format("%03d", i); //若未滿兩位數，前面補0
            String urlStr = urlPre + iStr + urlSuf;

            String lastStr = "_";

            //定義檔案名稱
            String fileNm = urlStr.substring(urlStr.lastIndexOf(lastStr)+1);
            download(fileNm, urlStr, folderNm);
        }
    }

    /**
     * 呼叫外部的API 取得資料
     * @param url API網址
     * @param requestData 請求參數
     * @return
     */
    private static String postDataToExternalAPI(String url, Map<String, Object> requestData){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return restTemplate.postForObject(url, requestData, String.class);
    }

    /**
     *
     * @param fileNm 創建檔名
     * @param urlStr 要載下來的url
     */
    private static void download(String fileNm, String urlStr, String folderNm) {

        if(IS_SLEEP){
            sleep(1000,3000);
        }

        File folder = new File(PATH_NAME + folderNm);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(folder, fileNm);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int fileSize = connection.getContentLength();

            try (InputStream is = connection.getInputStream();
                 FileOutputStream fos = new FileOutputStream(file)) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                long totalBytesRead = 0;

                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    int progress = (int) ((double) totalBytesRead / fileSize * 100);
                    System.out.print("\r下載中 " + fileNm + ": " + progress + "%");
                }
                System.out.println("\n下載完成: " + fileNm);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模擬人為的速度
     * @param min 最少毫秒
     * @param max 最多毫秒
     */
    private static void sleep(int min, int max){
        try {
            Thread.sleep((long) (Math.floor(Math.random()*(max - min))+min));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
