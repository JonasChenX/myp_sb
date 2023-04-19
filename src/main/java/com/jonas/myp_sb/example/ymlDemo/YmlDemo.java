package com.jonas.myp_sb.example.ymlDemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
public class YmlDemo {
    @Value("${name}")
    private String name;

    @Autowired
    private Yml yml;

    @Test
    public void testYml(){
        System.out.println(name);
    }

    @Test
    public void testYml2(){
        System.out.println(yml.toString());
    }
}
