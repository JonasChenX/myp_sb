package com.jonas.myp_sb.example.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {
    /**
     * 將字符串渲染到客戶端
     * @param response 渲染物件
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
