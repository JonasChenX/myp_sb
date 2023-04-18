package com.jonas.myp_sb.example.security;

import com.alibaba.fastjson.JSON;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import com.jonas.myp_sb.example.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        /**
         * 若token沒有認證成功
         * 則會跑AuthenticationEntryPoint 進行異常處理
         * */

        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "認證失敗，請重新登入");
        String jsonR = JSON.toJSONString(result);
        WebUtils.renderString(response, jsonR);
    }
}
