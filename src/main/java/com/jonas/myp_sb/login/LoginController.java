package com.jonas.myp_sb.login;

import com.jonas.myp_sb.annotation.LogAnnotation;
import com.jonas.myp_sb.example.exception.BusinessException;
import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    @LogAnnotation(methodName = "登入")
    public ResponseResult login(@RequestBody User user){
        if (!Strings.hasText(user.getUserName())){
            throw new BusinessException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        //登入
        return  new ResponseResult(HttpCodeEnum.SUCCESS,loginService.login(user));
    }

    @RequestMapping("/user/logout")
    @LogAnnotation(methodName = "登出")
    public ResponseResult logout(){
        //登出
        return  new ResponseResult(HttpCodeEnum.SUCCESS,loginService.logout());
    }
}
