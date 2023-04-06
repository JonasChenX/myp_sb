package com.jonas.myp_sb.example.ioDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
