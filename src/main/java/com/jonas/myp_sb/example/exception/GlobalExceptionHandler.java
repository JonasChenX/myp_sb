package com.jonas.myp_sb.example.exception;

import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import com.jonas.myp_sb.example.responseResult.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({SystemException.class})
    public ResponseResult systemExceptionHandler (SystemException e){
        log.error("出現異常! 錯誤訊息如下:", e);
        return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseResult<Object> toBusinessException(BusinessException e){
        log.error("出現業務邏輯異常如下:", e);
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseResult<Object> illegalParameterException(MethodArgumentNotValidException e){
        String message = e.getMessage();
        message = message.substring(message.lastIndexOf("[")+1, message.lastIndexOf("]")-1);
        log.error("資料校驗出現問題:{},異常類型:{}",message,e.getClass());
        return ResponseResult.errorResult(HttpCodeEnum.ILLEGAL_PARAMETER.getCode(),message);
    }

}
