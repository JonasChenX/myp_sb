package com.jonas.myp_sb.example.security;

import com.alibaba.fastjson.JSON;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import com.jonas.myp_sb.example.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        /**
         * 會藉由@PreAuthorize("hasAuthority()") 判斷是否有權限
         * 若沒有該權限則會跑AccessDeniedHandler 進行異常處理
         * */
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "權限不足");
        String jsonR = JSON.toJSONString(result);
        WebUtils.renderString(response, jsonR);
    }
}
