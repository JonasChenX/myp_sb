package com.jonas.myp_sb.example.ioDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class IoDemoService {

    @Autowired
    private Resources resources;

    /**
     * 讀取resources底下的txt檔案
     * **/
    public String readText(){
        String content = resources.readAsString("classpath:ioDemo/readText.txt");
        return content;
    }

    /**
     * 讀取resources底下的sql檔案
     * **/
    public String readSQL(){
        String content = resources.readAsString("classpath:ioDemo/readSQL.sql");
        return content;
    }

    /**
     * 下載功能
     * **/
    public DownloadableResource downloadFile(){
        String fileName = "test.png";
        InputStream inputStream = null;
        try {
            inputStream = resources.getResourceInputStream("classpath:ioDemo/"+fileName);
            ByteArrayResource resource = new ByteArrayResource(inputStream.readAllBytes());
            DownloadableResource downloadableResource = new DownloadableResource(resource, fileName);
            return downloadableResource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            resources.closeInputStream(inputStream);
        }
    }

    /**
     * 上傳功能
     * **/
    public String uploadFile(MultipartFile file) {
        try {
            resources.writeToFile(file.getOriginalFilename(), "testFolder", file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "寫入完成";
    }



}
