package com.jonas.myp_sb.controller;

import com.jonas.myp_sb.annotation.LogAnnotation;
import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/test1")
    @LogAnnotation(methodName = "測試")
    @PreAuthorize("hasAuthority('system:dept:list')")
    public ResponseResult test1(){
        //登入
        return  new ResponseResult(HttpCodeEnum.SUCCESS,"測試成功");
    }

    @GetMapping("/test2")
    @LogAnnotation(methodName = "測試")
    @PreAuthorize("hasAuthority('system:test:list')")
    public ResponseResult test2(){
        //登入
        return  new ResponseResult(HttpCodeEnum.SUCCESS,"測試成功");
    }

    @GetMapping("/test3")
    @LogAnnotation(methodName = "測試")
    @PreAuthorize("@SG.hasAuthority('system:dept:list')")
    public ResponseResult test3(){
        //登入
        return  new ResponseResult(HttpCodeEnum.SUCCESS,"測試成功");
    }
}
