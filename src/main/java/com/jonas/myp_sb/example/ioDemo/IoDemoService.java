package com.jonas.myp_sb.example.ioDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
