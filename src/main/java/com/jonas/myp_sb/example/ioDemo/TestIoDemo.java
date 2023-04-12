package com.jonas.myp_sb.example.ioDemo;

import com.jonas.myp_sb.annotation.LogAnnotation;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestIoDemo {
    @Autowired
    private IoDemoService ioDemoService;

    @GetMapping("/readText")
    @LogAnnotation(methodName = "讀取文件資料")
    public String readText() {
        String result = ioDemoService.readText();
        return result;
    }

    /**
     * 搭配ResponseResult物件統一回傳格式
     * **/
    @GetMapping("/readText2")
    public ResponseResult<String> readText2() {
        String result = ioDemoService.readText();
        return ResponseResult.success(result);
    }

    @GetMapping("/readSQL")
    public String readSQL() {
        String result = ioDemoService.readSQL();
        return result;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(){
        Resource result = ioDemoService.downloadFile();
        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + result.getFilename())
        .body(result);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("Filename: " + file.getOriginalFilename());
        System.out.println("Content type: " + file.getContentType());
        System.out.println("File size: " + file.getSize());
        return ResponseEntity.ok(ioDemoService.uploadFile(file));
    }
}
