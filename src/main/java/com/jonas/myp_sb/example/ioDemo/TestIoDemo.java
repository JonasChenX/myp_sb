package com.jonas.myp_sb.example.ioDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class TestIoDemo {
    @Autowired
    private IoDemoService ioDemoService;

    @GetMapping("/readText")
    public String readText() {
        String result = ioDemoService.readText();
        return result;
    }

    @GetMapping("/readSQL")
    public String readSQL() {
        String result = ioDemoService.readSQL();
        return result;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        Resource result = ioDemoService.downloadFile();
        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + result.getFilename())
        .body(result);
    }
}
