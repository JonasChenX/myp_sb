package com.jonas.myp_sb.aspect;

import com.alibaba.fastjson2.JSON;
import com.jonas.myp_sb.annotation.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.jonas.myp_sb.annotation.LogAnnotation)")
    public void logAspect() {}

    @Before("logAspect()")
    public void beforeLog(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String methodName = joinPoint.getSignature().getName();
        log.info("============== Method " + methodName + "() begin ==============");
        //執行時間
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = df.format(date);
        log.info("Time --> " + time);
        //URL
        log.info("URL --> " + request.getRequestURL());
        //描述訊息
        LogAnnotation logAnnotation = getLogAnnotation(joinPoint);
        log.info("MethodName --> " + logAnnotation.methodName());
        //請求方法
        log.info("HTTP Method --> " + request.getMethod());
        //執行方法
        log.info("Class Method --> " + joinPoint.getSignature().getDeclaringTypeName() + "." + methodName);
        //IP
        log.info("IP --> " + request.getRemoteHost());
        //請求參數
        log.info("RequestParam --> " + JSON.toJSONString(joinPoint.getArgs()));
    }

    @After("logAspect()")
    public void afterLog(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        log.info("============== Method " + methodName + "() end ==============");
    }

    private LogAnnotation getLogAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        return annotation;
    }
}
