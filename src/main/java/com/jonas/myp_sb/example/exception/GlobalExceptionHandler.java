package com.jonas.myp_sb.example.exception;

import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler (SystemException e){
        log.error("出現異常! 錯誤訊息如下:",e);
        return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler (Exception e){
        log.error("出現異常! 錯誤訊息如下:",e);
        return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR.getCode(),e.toString());
    }

}
