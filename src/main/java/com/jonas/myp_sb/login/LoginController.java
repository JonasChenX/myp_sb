package com.jonas.myp_sb.login;

import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        //登入
        return  new ResponseResult(HttpCodeEnum.SUCCESS,loginService.login(user));
    }
}
