package com.jonas.myp_sb.controller;

import com.jonas.myp_sb.annotation.LogAnnotation;
import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/test")
    @LogAnnotation(methodName = "測試")
    @PreAuthorize("hasAuthority('test')")
    public ResponseResult test(){
        //登入
        return  new ResponseResult(HttpCodeEnum.SUCCESS,"測試成功");
    }
}
