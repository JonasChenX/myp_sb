package com.jonas.myp_sb.example.exception;

import com.jonas.myp_sb.example.responseResult.HttpCodeEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BusinessException extends RuntimeException{
    private final Integer code;
    private final String message;

    public BusinessException(HttpCodeEnum httpCodeEnum){
        super(httpCodeEnum.getMessage());
        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMessage();
    }
}
