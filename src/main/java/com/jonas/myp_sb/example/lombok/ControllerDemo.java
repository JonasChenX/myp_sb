package com.jonas.myp_sb.example.lombok;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //日誌
@RestController
public class ControllerDemo {

    @GetMapping("/userLombokDemo")
    public UserLombokDemo LombokTest(){
        UserLombokDemo userLombokDemo = new UserLombokDemo();
        userLombokDemo.setAge(12);
        userLombokDemo.setName("小明");
        log.info(userLombokDemo.toString());
        return userLombokDemo;
    }
}
