package com.jonas.myp_sb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解决跨域问题
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //設置充許跨域的路徑
        registry.addMapping("/**")
                //設置可跨域請求的域名
                .allowedOrigins("*")
                //設置是否充許cookie
                .allowCredentials(true)
                //設置充許的請求方式
                .allowedMethods("GET","POST","DELETE","PUT")
                //設置充許的header屬性
                .allowedHeaders("*")
                //設置充許時間
                .maxAge(3600);
    }
}
