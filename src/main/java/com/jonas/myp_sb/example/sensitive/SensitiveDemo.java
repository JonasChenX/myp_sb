package com.jonas.myp_sb.example.sensitive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SensitiveDemo {

    @GetMapping("/sensitiveTest")
    public UserVo sensitiveTest(){
        UserVo userVo = new UserVo();
        userVo.setEmail("12345@gmail.com");
        userVo.setPhone("0911234567");
        userVo.setIdCard("A123456789");
        userVo.setAddress("台北市信義區慶源路二段11巷99號");
        userVo.setName("一二三四");
        return userVo;
    }
}
