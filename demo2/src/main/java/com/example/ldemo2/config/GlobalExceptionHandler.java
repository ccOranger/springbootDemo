package com.example.ldemo2.config;

import com.example.ldemo2.utils.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 *  @author: 李臣臣
 *  @Date: 2020/11/19 0019 18:14
 *  @Description: 全局异常捕获
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return Result.error().setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
    }
    /**
     * 参数转换异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Result.error().setMessage(e.getCause().getMessage());
    }
}
